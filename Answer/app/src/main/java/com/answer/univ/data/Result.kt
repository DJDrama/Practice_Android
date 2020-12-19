package com.answer.univ.data

data class Result(
    val isSuccess: Boolean,
    val error: String? = null
)


const val UNKNOWN_ERROR = "Unknown Error"