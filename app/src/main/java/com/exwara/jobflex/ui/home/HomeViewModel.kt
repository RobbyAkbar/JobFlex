package com.exwara.jobflex.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.exwara.jobflex.core.data.Resource
import com.exwara.jobflex.core.domain.model.SearchItem
import com.exwara.jobflex.core.domain.usecase.IJobUseCase
import okhttp3.RequestBody

/**
 * Created by robby on 01/06/21.
 */
class HomeViewModel(private val jobUseCase: IJobUseCase): ViewModel() {
    fun recommendedJob(idUser: RequestBody): LiveData<Resource<List<SearchItem>>> {
        return jobUseCase.recommendJob(idUser)
    }
}