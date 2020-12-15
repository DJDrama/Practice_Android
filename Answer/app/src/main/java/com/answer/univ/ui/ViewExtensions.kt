package com.answer.univ.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity


@SuppressLint("RestrictedApi")
fun Activity.hideActionBar(){
    (this as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
    supportActionBar?.hide()
}
fun Activity.showActionBar(){
    (this as AppCompatActivity).supportActionBar?.show()
}