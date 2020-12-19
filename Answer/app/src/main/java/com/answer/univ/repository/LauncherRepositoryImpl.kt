package com.answer.univ.repository

import android.content.Context
import com.answer.univ.R
import com.answer.univ.data.getInterestList
import com.answer.univ.data.getUniversityList
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class LauncherRepositoryImpl(

) : LauncherRepository {

    override fun getUniversities(): Set<String> = getUniversityList()
    override fun getInterests(): List<String> = getInterestList()
    override fun register(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phoneNum: String,
        university: String,
        major: String,
        interest: String
    ) {
        TODO("Not yet implemented")
    }


}