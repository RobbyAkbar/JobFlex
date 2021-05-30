package com.exwara.jobflex.core.domain.repository

import androidx.lifecycle.LiveData
import com.dicoding.tourismapp.core.data.Resource
import com.exwara.jobflex.core.domain.model.Pdf
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IJobRepository {
    fun uploadPDF(file: MultipartBody.Part, name: RequestBody, fileName: String): LiveData<Resource<Pdf>>
}