package com.dj.sampleapp.data.responses

import com.dj.sampleapp.data.model.PopularCard

data class PhotoFeedResponse(
    val ok: Boolean,
    val msg: String?,
    val cards: List<PopularCard>,
)