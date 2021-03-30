package com.dj.sampleapp.ui

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

fun Context.showToast(msg: String, length: Int = LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}