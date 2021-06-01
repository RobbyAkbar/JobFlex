package com.exwara.jobflex.core.data.source.remote.network

import com.exwara.jobflex.core.data.source.remote.response.SearchResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SearchService {
    @POST("search")
    fun searchJob(@Body requestBody: RequestBody): Call<List<SearchResponse>>

    @POST("getRecommendation")
    fun recommendJob(@Body requestBody: RequestBody): Call<List<SearchResponse>>
}