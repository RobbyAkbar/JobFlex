package com.exwara.jobflex.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.exwara.jobflex.core.data.Resource
import com.exwara.jobflex.core.ui.SearchJobAdapter
import com.exwara.jobflex.core.ui.ViewModelFactory
import com.exwara.jobflex.databinding.FragmentSearchBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            searchViewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]
            searchAdapter = SearchJobAdapter()

            val jobName = arguments?.getString("jobName")
            if (jobName != null) {
                searchJob(jobName)
            }

            with(binding.rvResultJob) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = searchAdapter
            }
        }
    }

    private fun searchJob(jobName: String){
        val jsonObject = JSONObject()
        jsonObject.put("toSearch", jobName)
        val jsonObjectString = jsonObject.toString()

        val requestBody: RequestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        searchViewModel.searchJob(requestBody).observe(viewLifecycleOwner,
            {res->
                if (res != null) {
                    when (res) {
                        is Resource.Loading -> {
                            binding.pgrBarSearch.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.pgrBarSearch.visibility = View.GONE
                            searchAdapter.setData(res.data)
                            searchAdapter.notifyDataSetChanged()
                            Toast.makeText(binding.root.context, "success", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            binding.pgrBarSearch.visibility = View.GONE
                            Toast.makeText(binding.root.context, "error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvResultJob.adapter = null
        _binding = null
    }
}