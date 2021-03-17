package com.movierecom.www.util

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.DecimalFormat

fun String.getCommaString(): String {
    val formatter = DecimalFormat ("#,###,###")
    return formatter.format(toLong())
}

fun Context.hideKeyboard(view: View){
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}