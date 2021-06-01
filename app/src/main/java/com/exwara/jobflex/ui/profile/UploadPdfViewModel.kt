package com.exwara.jobflex.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.exwara.jobflex.core.data.Resource
import com.exwara.jobflex.core.domain.model.PdfItem
import com.exwara.jobflex.core.domain.usecase.IJobUseCase
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadPdfViewModel(private val jobUseCase: IJobUseCase): ViewModel() {
    fun uploadPdf(file: MultipartBody.Part, name: RequestBody, fileName: String): LiveData<Resource<PdfItem>> {
        return jobUseCase.uploadPDF(file, name, fileName)
    }
}