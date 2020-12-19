package com.answer.univ.repository

import kotlinx.coroutines.flow.Flow

interface LauncherRepository {

    fun getUniversities() : Set<String>
    fun getInterests(): List<String>

    fun register(email: String, password: String, name: String, nickName: String, phoneNum: String, university: String, major: String, interest: String)
}