package com.exwara.jobflex.core.data.source.remote.network

import com.exwara.jobflex.core.data.source.remote.response.SearchResponseItem
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SearchService {
    @POST("search")
    fun searchJob(@Body requestBody: RequestBody): Call<List<SearchResponseItem>>
}