package com.answer.univ.repository

import kotlinx.coroutines.flow.Flow

interface LauncherRepository {

    fun getUniversities() : List<String>
}