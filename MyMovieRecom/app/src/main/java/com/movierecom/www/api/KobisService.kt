package com.movierecom.www.api

import com.movierecom.www.util.KOBIS_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KobisService {

    @GET("boxoffice/searchDailyBoxOfficeList.json")
    suspend fun searchMovies(
        @Query(value = "key") key: String = KOBIS_KEY,
        @Query(value = "targetDt") date: String,
        @Query(value = "itemPerPage") itemPerPage: Int=10
    ): Response<KobisResponse>
}