package com.movierecom.www.model

import com.squareup.moshi.Json

data class NaverMovie(
    val title: String,
    val link: String,
    val image: String,
    @Json(name="subtitle")
    val subTitle: String,
    val pubDate: String,
    val director: String,
    val actor: String,
    val userRating: Float
)