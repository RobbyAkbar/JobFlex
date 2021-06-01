package com.exwara.jobflex.core.data.source.remote.network

import com.exwara.jobflex.core.data.source.remote.response.PdfResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers(
        "Content-Type: application/pdf"
    )
    @POST
    fun uploadPDF(@Body name: RequestBody,
                  @Url url: String
    ): Call<PdfResponse>
}

// @Part("file") name: RequestBody, @Path("fileName") fileName: String): Call<PdfResponse>