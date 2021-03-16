package com.movierecom.www.util

import java.text.DecimalFormat

fun String.getCommaString(): String {
    val formatter = DecimalFormat ("#,###,###")
    return formatter.format(toLong())
}