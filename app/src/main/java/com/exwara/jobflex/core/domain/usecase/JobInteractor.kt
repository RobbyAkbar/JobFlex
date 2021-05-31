package com.exwara.jobflex.core.domain.usecase

import androidx.lifecycle.LiveData
import com.dicoding.tourismapp.core.data.Resource
import com.exwara.jobflex.core.domain.model.Pdf
import com.exwara.jobflex.core.domain.model.SearchItem
import com.exwara.jobflex.core.domain.repository.IJobRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class JobInteractor(private val jobRepository: IJobRepository): IJobUseCase {
    override fun uploadPDF(
        file: MultipartBody.Part,
        name: RequestBody,
        fileName: String
    ): LiveData<Resource<Pdf>> {
        return jobRepository.uploadPDF(file, name, fileName)
    }

    override fun searchJob(toSearch: RequestBody): LiveData<Resource<List<SearchItem>>> {
        return jobRepository.searchJob(toSearch)
    }
}