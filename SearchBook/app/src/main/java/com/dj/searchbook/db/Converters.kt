package com.dj.searchbook.db

import androidx.room.TypeConverter
import com.dj.searchbook.util.DateUtils
import java.util.Date

class Converters {

    @TypeConverter
    fun listToString(list: List<String>): String {
        val sb = StringBuilder()
        if (list.isEmpty()) return ""
        list.forEach {
            sb.append(it).append(",")
        }
        sb.setLength(sb.length - 1)
        return sb.toString()
    }

    @TypeConverter
    fun stringToList(str: String): List<String> {
        if (str.isEmpty()) return emptyList()
        return str.split(",").toList()
    }

    @TypeConverter
    fun dateToLong(date: Date): Long {
        return DateUtils.dateToLong(date)
    }

    @TypeConverter
    fun longToDate(long: Long): Date {
        return DateUtils.longToDate(long)
    }
}
