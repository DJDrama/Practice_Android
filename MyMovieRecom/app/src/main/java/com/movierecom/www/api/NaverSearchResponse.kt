package com.movierecom.www.api

import com.movierecom.www.model.NaverMovie

data class NaverSearchResponse(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NaverMovie>
)