package com.exwara.jobflex.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exwara.jobflex.core.data.source.remote.network.ApiResponse
import com.exwara.jobflex.core.data.source.remote.network.ApiService
import com.exwara.jobflex.core.data.source.remote.response.PdfResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun uploadPDF(
        file: MultipartBody.Part,
        name: RequestBody,
        fileName: String
    ): LiveData<ApiResponse<PdfResponse>> {
        val resultData = MutableLiveData<ApiResponse<PdfResponse>>()

        val client = apiService.uploadPDF(file, name, fileName)

        client.enqueue(object : Callback<PdfResponse> {
            override fun onResponse(
                call: Call<PdfResponse>,
                response: Response<PdfResponse>
            ) {
                val data = response.body()
                resultData.value = if (data != null) ApiResponse.Success(data) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<PdfResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }
}