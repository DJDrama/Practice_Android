package com.movierecom.www.api

import com.movierecom.www.util.secretId
import com.movierecom.www.util.clientId
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverSearchService {

    @GET("v1/search/movie.json")
    suspend fun searchMovies(
        @Header(value = "X-Naver-Client-Id") xNaverClientId: String = clientId,
        @Header(value = "X-Naver-Client-Secret") xNaverClientSecret: String = secretId,
        @Query(value = "query") query: String,
        @Query(value = "display") display: Int=10
    ): Response<SearchResponse>
}