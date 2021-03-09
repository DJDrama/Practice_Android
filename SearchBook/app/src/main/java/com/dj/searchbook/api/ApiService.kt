package com.dj.searchbook.api

import com.dj.searchbook.data.response.SearchResponse
import com.dj.searchbook.util.PAGINATION_SIZE
import com.dj.searchbook.util.REST_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("/v3/search/book")
    suspend fun getBookSearchResults(
        @Header("Authorization") header: String= "KakaoAK $REST_API_KEY",
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = PAGINATION_SIZE,
        @Query("target") target: String? = null,
    ): Response<SearchResponse>
}

