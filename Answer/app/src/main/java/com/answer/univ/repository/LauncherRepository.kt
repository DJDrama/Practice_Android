package com.answer.univ.repository

import com.answer.univ.data.Result
import kotlinx.coroutines.flow.Flow

interface LauncherRepository {

    fun getUniversities(): Set<String>
    fun getInterests(): List<String>

    suspend fun login(email: String, password: String): Result

    suspend fun register(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phoneNum: String,
        university: String,
        major: String,
        interest: String
    ): Result
}