package com.answer.univ.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}

@SuppressLint("RestrictedApi")
fun Activity.hideActionBar(){
    (this as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
    supportActionBar?.hide()
}
fun Activity.showActionBar(){
    (this as AppCompatActivity).supportActionBar?.show()
}