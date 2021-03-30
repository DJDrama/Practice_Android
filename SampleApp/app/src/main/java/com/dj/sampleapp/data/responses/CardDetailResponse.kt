package com.dj.sampleapp.data.responses

import com.dj.sampleapp.data.model.PopularCard
import com.dj.sampleapp.data.model.PopularUser
import com.squareup.moshi.Json

data class CardDetailResponse(
    val ok: Boolean,
    val msg: String?,
    val card : PopularCard,
    val user: PopularUser,
    @Json(name="recommend_cards")
    val recommendCards: List<PopularCard>
)