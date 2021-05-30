package com.exwara.jobflex.core.data.source.remote.network

import com.exwara.jobflex.core.data.source.remote.response.PdfResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers(
        "Content-Type: multipart/form-data"
    )
    @Multipart
    @POST("job-flex-storage/o?uploadType=media&name=pdf/{fileName}")
    fun uploadPDF(@Part file: MultipartBody.Part,
                  @Part("file") name: RequestBody,
                  @Path("fileName") fileName: String): Call<PdfResponse>
}

// @Part("file") name: RequestBody, @Path("fileName") fileName: String): Call<PdfResponse>