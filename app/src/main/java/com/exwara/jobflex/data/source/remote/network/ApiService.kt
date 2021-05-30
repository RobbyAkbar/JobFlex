package com.exwara.jobflex.data.source.remote.network

import com.exwara.jobflex.core.data.source.remote.response.PdfResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers(
        "Authorization: Bearer ya29.a0AfH6SMCxxMPepWTl9dyCoGYMgqFM0wntCyM74sTEDaxubOMsNOoMk8u-nsm9pnMy6a8uj41HiMxSw-0NvhnXzsudSZZlNTLv8UUeEtUfqcZOmuRJvxyhypgPNWAC3-x4Br4x829EjfA1ZqLP7eeVE7hEtw73",
        "Content-Type: application/pdf"
    )
    @Multipart
    @POST("job-flex-storage/o?uploadType=media&name=pdf/{fileName}.pdf")
    fun uploadPDF(@Part file: MultipartBody.Part,
                  @Part("file") name: RequestBody,
                  @Path("fileName") fileName: String): Call<PdfResponse>
}