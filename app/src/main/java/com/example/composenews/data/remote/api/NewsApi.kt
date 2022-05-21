package com.example.composenews.data.remote.api

import com.example.composenews.data.remote.models.SourceResponse
import com.example.composenews.data.remote.models.TopHeadlinesResponse
import com.example.composenews.utils.Constants.API_KEY
import com.example.composenews.utils.Constants.PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<TopHeadlinesResponse>

    @GET("sources")
    suspend fun getAllSource(
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<SourceResponse>

    @GET("everything")
    suspend fun getEverything(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("q") query: String = "bitcoin",
        @Query("pageSize") pageSize: Int = PAGE_SIZE,
        @Query("page") page: Int = 1
    ): Response<TopHeadlinesResponse>
}