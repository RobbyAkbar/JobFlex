package com.exwara.jobflex.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.exwara.jobflex.databinding.FragmentHomeBinding
import com.exwara.jobflex.utils.DataDummy
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var jobAdapter: JobAdapter

    private val sliderHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            sliderAdapter = SliderAdapter()
            jobAdapter = JobAdapter()

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(40))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }

            binding?.apply {
                with(vpCarousel){
                    adapter = sliderAdapter
                    offscreenPageLimit = 3
                    getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                    setPageTransformer(compositePageTransformer)
                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            sliderHandler.removeCallbacks(sliderRunnable)
                            sliderHandler.postDelayed(sliderRunnable, 3000)
                        }
                    })
                }
                with(rvJob) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = jobAdapter
                }
            }

            sliderAdapter.listItems = DataDummy.generateDummySlider()
            jobAdapter.updateWith(DataDummy.generateDummyJobs(), 3)
        }
    }

    private val sliderRunnable = Runnable {
        binding?.vpCarousel?.let {
            if (it.currentItem == sliderAdapter.listItems.size - 1){
                it.currentItem = 0
            } else it.currentItem = it.currentItem + 1
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

}