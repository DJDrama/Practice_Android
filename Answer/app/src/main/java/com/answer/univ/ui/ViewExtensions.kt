package com.answer.univ.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}

fun Context.showToast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

@SuppressLint("RestrictedApi")
fun Activity.hideActionBar(){
    (this as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
    supportActionBar?.hide()
}
fun Activity.showActionBar(){
    (this as AppCompatActivity).supportActionBar?.show()
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun String.isBlankOrEmpty() = isBlank() || isEmpty()