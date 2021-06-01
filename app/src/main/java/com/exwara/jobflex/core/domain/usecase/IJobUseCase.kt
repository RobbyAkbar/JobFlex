package com.exwara.jobflex.core.domain.usecase

import androidx.lifecycle.LiveData
import com.exwara.jobflex.core.data.Resource
import com.exwara.jobflex.core.domain.model.PdfItem
import com.exwara.jobflex.core.domain.model.SearchItem
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IJobUseCase {
    fun uploadPDF(file: MultipartBody.Part, name: RequestBody, fileName: String): LiveData<Resource<PdfItem>>
    fun searchJob(toSearch: RequestBody): LiveData<Resource<List<SearchItem>>>
    fun recommendJob(idUser: RequestBody): LiveData<Resource<List<SearchItem>>>
}