package com.exwara.jobflex.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.exwara.jobflex.R
import com.exwara.jobflex.core.data.Resource
import com.exwara.jobflex.core.ui.ViewModelFactory
import com.exwara.jobflex.databinding.FragmentHomeBinding
import com.exwara.jobflex.core.utils.DataDummy
import com.exwara.jobflex.core.utils.Preferences
import com.exwara.jobflex.ui.profile.UploadPdfActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var jobAdapter: JobAdapter
    private var listSize: Int = 0
    private var expand: Boolean = false

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
            val factory = ViewModelFactory.getInstance(requireActivity())
            homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
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

                tvShowMore.setOnClickListener {
                    if (expand) {
                        tvShowMore.text = getString(R.string.txt_show_more)
                        jobAdapter.itemSize = 3
                        jobAdapter.notifyDataSetChanged()
                        expand = false
                    } else {
                        tvShowMore.text = getString(R.string.txt_show_less)
                        jobAdapter.itemSize = listSize
                        jobAdapter.notifyDataSetChanged()
                        expand = true
                    }
                }

                btnUpload.setOnClickListener {
                    val intent = Intent(requireContext(), UploadPdfActivity::class.java)
                    startActivity(intent)
                }
            }

            sliderAdapter.listItems = DataDummy.generateDummySlider()
            getRecommendationJob()
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
        if (listSize == 0) getRecommendationJob()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    private fun getRecommendationJob(){
        val jsonObject = JSONObject()
        jsonObject.put("id", Preferences().getUserId(requireContext()))
        val jsonObjectString = jsonObject.toString()

        val requestBody: RequestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        homeViewModel.recommendedJob(requestBody).observe(viewLifecycleOwner,
            {res->
                if (res != null) {
                    when (res) {
                        is Resource.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            res.data?.let {
                                if (it.isEmpty()) {
                                    binding?.btnUpload?.visibility = View.VISIBLE
                                } else {
                                    jobAdapter.updateWith(it, 3)
                                    listSize = it.size
                                    binding?.tvShowMore?.visibility = View.VISIBLE
                                    binding?.btnUpload?.visibility = View.GONE
                                }
                            }
                            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

}