package com.exwara.jobflex.core.domain.usecase

import androidx.lifecycle.LiveData
import com.dicoding.tourismapp.core.data.Resource
import com.exwara.jobflex.core.domain.model.Pdf
import com.exwara.jobflex.core.domain.model.SearchItem
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IJobUseCase {
    fun uploadPDF(file: MultipartBody.Part, name: RequestBody, fileName: String): LiveData<Resource<Pdf>>
    fun searchJob(toSearch: RequestBody): LiveData<Resource<List<SearchItem>>>
}