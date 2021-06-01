package com.exwara.jobflex.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.tourismapp.core.data.Resource
import com.exwara.jobflex.core.domain.model.Pdf
import com.exwara.jobflex.core.domain.usecase.IJobUseCase
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(private val jobUseCase: IJobUseCase): ViewModel() {
}