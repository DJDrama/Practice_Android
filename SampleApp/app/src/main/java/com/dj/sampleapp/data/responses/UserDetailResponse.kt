package com.dj.sampleapp.data.responses

import com.dj.sampleapp.data.model.PopularUser

data class UserDetailResponse(
    val user: PopularUser,
    val ok: Boolean,
    val msg: String?
)
