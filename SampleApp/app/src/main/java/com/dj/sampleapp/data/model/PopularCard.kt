package com.dj.sampleapp.data.model

import com.squareup.moshi.Json


data class PopularCard(
    @Json(name="user_id")
    val userId: Int,
    @Json(name="img_url")
    val imgUrl: String,
    val description: String,
    val id: Int
)




