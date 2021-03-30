package com.dj.sampleapp.data.responses

import com.dj.sampleapp.data.model.PopularCard
import com.dj.sampleapp.data.model.PopularUser
import com.squareup.moshi.Json

data class HomeDataResponse(
    @Json(name="popular_cards")
    val popularCards: List<PopularCard>?,
    @Json(name="popular_users")
    val popularUsers: List<PopularUser>?,
    val ok: Boolean,
    val msg: String?
)
