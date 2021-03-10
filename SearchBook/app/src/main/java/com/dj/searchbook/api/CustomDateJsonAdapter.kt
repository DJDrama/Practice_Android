package com.dj.searchbook.api

import com.dj.searchbook.util.EMPTY_DATE_STRING
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CustomDateJsonAdapter {
    @ToJson
    fun toJson(value: Date): String {
        return ""
    }

    @FromJson
    fun fromJson(value: String): Date? {
        if(value.isEmpty()) {
            return FORMATTER.parse(EMPTY_DATE_STRING)
        }
        return FORMATTER.parse(value)
    }

    companion object {
        private val FORMATTER =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ", Locale.KOREA)
    }
}