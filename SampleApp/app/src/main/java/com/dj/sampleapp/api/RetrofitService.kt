package com.dj.sampleapp.api

import com.dj.sampleapp.data.model.UserRegistration
import com.dj.sampleapp.data.responses.CardDetailResponse
import com.dj.sampleapp.data.responses.HomeDataResponse
import com.dj.sampleapp.data.responses.PhotoFeedResponse
import com.dj.sampleapp.data.responses.UserDetailResponse
import com.dj.sampleapp.other.PAGINATION_SIZE
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @POST("users/sign_in")
    suspend fun login(
        @Body userRegistration: UserRegistration,
    )

    @POST("users")
    suspend fun registerUser(
        @Body userRegistration: UserRegistration,
    )

    @GET("home")
    suspend fun getHomeData(): HomeDataResponse

    @GET("cards")
    suspend fun getPhotoFeed(
        @Query("page") page: Int,
        @Query("per") per: Int = PAGINATION_SIZE,
    ): PhotoFeedResponse

    @GET("cards/{id}")
    suspend fun getCardDetail(
        @Path("id") id: Int,
    ) : CardDetailResponse

    @GET("users/{id}")
    suspend fun getUserDetail(
        @Path("id") id: Int,
    ) : UserDetailResponse
}