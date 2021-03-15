package com.movierecom.www.model

data class NaverMovie(
    val title: String,
    val link: String,
    val image: String,
    val subTitle: String,
    val pubDate: String,
    val director: String,
    val actor: String,
    val userRating: Float
)