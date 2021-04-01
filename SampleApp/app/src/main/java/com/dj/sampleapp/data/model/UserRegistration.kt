package com.dj.sampleapp.data.model

data class UserRegistration(
    val nickname: String,
    val introduction: String? = null,
    val pwd: String
)