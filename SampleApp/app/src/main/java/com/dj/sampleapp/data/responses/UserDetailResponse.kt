package com.dj.sampleapp.data.responses

import com.dj.sampleapp.data.model.PopularCard
import com.dj.sampleapp.data.model.PopularUser
import com.squareup.moshi.Json

data class UserDetailResponse(
    val user: PopularUser,
    val ok: Boolean,
    val msg: String?
)
