package com.answer.univ.repository

import android.content.Context
import com.answer.univ.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class LauncherRepositoryImpl(
    @ApplicationContext
    private val app: Context
) : LauncherRepository {

    override fun getUniversities(): List<String> {
        val list = LinkedList<String>()
        val inputStream = app.resources.openRawResource(R.raw.univ_list)
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var temp: String? = null
        while (bufferedReader.readLine()
                .also {
                    it?.let {
                        temp = it
                    }
                } != null
        ) {
            temp?.let {
                list.add(temp ?: "")
            }
        }

        list.addFirst("대학교를 선택하세요.")
        return list
    }
}