package com.dicoding.tourismapp.core.di

import android.content.Context
import com.exwara.jobflex.core.data.JobRepository
import com.exwara.jobflex.core.data.source.local.LocalDataSource
import com.exwara.jobflex.core.data.source.remote.RemoteDataSource
import com.exwara.jobflex.core.data.source.remote.network.ApiConfig
import com.exwara.jobflex.core.domain.repository.IJobRepository
import com.exwara.jobflex.core.domain.usecase.IJobUseCase
import com.exwara.jobflex.core.domain.usecase.JobInteractor
import com.exwara.jobflex.core.utils.AppExecutors

object Injection {
    private fun provideRepository(context: Context): IJobRepository {
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService())
        val localDataSource = LocalDataSource()
        val appExecutors = AppExecutors()

        return JobRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideJobUseCase(context: Context): IJobUseCase {
        val repository = provideRepository(context)
        return JobInteractor(repository)
    }
}
