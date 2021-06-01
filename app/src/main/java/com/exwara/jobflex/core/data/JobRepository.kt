package com.exwara.jobflex.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.exwara.jobflex.core.data.source.local.LocalDataSource
import com.exwara.jobflex.core.data.source.remote.RemoteDataSource
import com.exwara.jobflex.core.data.source.remote.network.ApiResponse
import com.exwara.jobflex.core.domain.model.PdfItem
import com.exwara.jobflex.core.domain.model.SearchItem
import com.exwara.jobflex.core.domain.repository.IJobRepository
import com.exwara.jobflex.core.utils.AppExecutors
import com.exwara.jobflex.core.utils.DataMapper
import okhttp3.MultipartBody
import okhttp3.RequestBody

class JobRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IJobRepository {

    companion object {
        @Volatile
        private var instance: JobRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): JobRepository =
            instance ?: synchronized(this) {
                instance ?: JobRepository(remoteData, localData, appExecutors)
            }
    }

    override fun uploadPDF(
        file: MultipartBody.Part,
        name: RequestBody,
        fileName: String
    ): LiveData<Resource<PdfItem>> {
        val result = MediatorLiveData<Resource<PdfItem>>()
        val apiResponse = remoteDataSource.uploadPDF(file, name, fileName)
        result.value = Resource.Loading(null)
        result.addSource(apiResponse) { response ->
            when (response) {
                is ApiResponse.Success ->
                    result.value =
                        Resource.Success(DataMapper.mapPdfResponsesToPdfDomain(response.data))
                is ApiResponse.Error -> {
                    result.value = Resource.Error(response.errorMessage, null)
                }
                ApiResponse.Empty -> TODO()
            }
        }
        return result
    }

    override fun searchJob(toSearch: RequestBody): LiveData<Resource<List<SearchItem>>> {
        val result = MediatorLiveData<Resource<List<SearchItem>>>()
        val apiResponse = remoteDataSource.searchJob(toSearch)
        result.value = Resource.Loading(null)
        result.addSource(apiResponse) { response ->
            when (response) {
                is ApiResponse.Success ->
                    result.value =
                        Resource.Success(DataMapper.mapSearchResponsesToSearchDomain(response.data))
                is ApiResponse.Error ->
                    result.value = Resource.Error(response.errorMessage, null)
                is ApiResponse.Empty -> TODO()
            }
        }
        return result
    }

    override fun recommendJob(idUser: RequestBody): LiveData<Resource<List<SearchItem>>> {
        val result = MediatorLiveData<Resource<List<SearchItem>>>()
        val apiResponse = remoteDataSource.getRecommendationJob(idUser)
        result.value = Resource.Loading(null)
        result.addSource(apiResponse) { response ->
            when (response) {
                is ApiResponse.Success ->
                    result.value =
                        Resource.Success(DataMapper.mapSearchResponsesToSearchDomain(response.data))
                is ApiResponse.Error ->
                    result.value = Resource.Error(response.errorMessage, null)
                is ApiResponse.Empty -> TODO()
            }
        }
        return result
    }
}