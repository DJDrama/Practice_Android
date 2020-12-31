package com.example.jetpackcomposepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /** Field Injection **/
    @Inject
    lateinit var str: String

    @Inject
    lateinit var app: BaseApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("Test", "String: $str")
        Log.e("Test", "App: $app")

    }
}