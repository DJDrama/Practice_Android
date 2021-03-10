package com.dj.searchbook.util

import java.text.NumberFormat
import java.util.Locale

fun getNumberFormattedString(value: Int): String {
    return NumberFormat.getInstance(Locale.KOREA).format(value)
}
