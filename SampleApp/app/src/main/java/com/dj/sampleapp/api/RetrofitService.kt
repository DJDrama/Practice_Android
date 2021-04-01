package com.dj.sampleapp.api

import com.dj.sampleapp.data.model.UserRegistration
import com.dj.sampleapp.data.responses.*
import com.dj.sampleapp.other.PAGINATION_SIZE
import retrofit2.http.*

interface RetrofitService {
    @POST("users/sign_in")
    suspend fun login(
        @Body userRegistration: UserRegistration,
    ): LogInResponse

    @POST("users")
    suspend fun registerUser(
        @Body userRegistration: UserRegistration,
    ): RegistrationResponse

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
    ): CardDetailResponse

    @GET("users/{id}")
    suspend fun getUserDetail(
        @Path("id") id: Int,
    ): UserDetailResponse
}