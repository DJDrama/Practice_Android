package com.movierecom.www.api

import com.movierecom.www.model.TmdbMovie
import com.movierecom.www.util.KOBIS_KEY
import com.movierecom.www.util.TMDB_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    @GET("search/movie")
    suspend fun getMovies(
        @Query(value = "api_key") key: String = TMDB_KEY,
        @Query(value = "language") language: String = "ko-KR",
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int = 1,
        @Query(value = "include_adult") include_adult: Boolean = false,
    ): Response<TmdbMovieResponse>

    @GET("movie/{movieId}/videos")
    suspend fun getTrailer(
        @Path(value = "movieId") movieId: Int,
        @Query(value = "api_key") api_key: String = TMDB_KEY,
        @Query(value = "language") language: String = "ko-KR",
    ): Response<TmdbTrailerResponse>
}