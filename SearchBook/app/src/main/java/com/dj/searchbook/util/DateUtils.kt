package com.dj.searchbook.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("yyyy-MM-dd")

    fun dateToString(date: Date): String {
        return sdf.format(date)
    }

    fun dateToLong(date: Date): Long {
        return date.time
    }

    fun longToDate(long: Long): Date {
        return Date(long)
    }
}
