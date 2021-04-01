package com.dj.sampleapp.data.responses

import com.squareup.moshi.Json

data class LogInResponse(
    val ok: Boolean,
    @Json(name = "error_msg")
    val errorMsg: String?,
    @Json(name = "user_id")
    val userId: Int?,
)