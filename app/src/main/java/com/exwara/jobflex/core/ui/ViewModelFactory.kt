package com.exwara.jobflex.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.tourismapp.core.di.Injection
import com.exwara.jobflex.core.domain.usecase.IJobUseCase
import com.exwara.jobflex.ui.profile.ProfileViewModel
import com.exwara.jobflex.ui.profile.UploadPdfViewModel

class ViewModelFactory private constructor(private val jobUseCase: IJobUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideJobUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(jobUseCase) as T
            }
            modelClass.isAssignableFrom(UploadPdfViewModel::class.java) -> {
                UploadPdfViewModel(jobUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}