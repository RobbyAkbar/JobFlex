package com.exwara.jobflex.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.exwara.jobflex.core.data.Resource
import com.exwara.jobflex.core.domain.model.SearchItem
import com.exwara.jobflex.core.domain.usecase.IJobUseCase
import okhttp3.RequestBody

class SearchViewModel(private val jobUseCase: IJobUseCase): ViewModel() {
    fun searchJob(toSearch: RequestBody): LiveData<Resource<List<SearchItem>>> {
        return jobUseCase.searchJob(toSearch)
    }
}