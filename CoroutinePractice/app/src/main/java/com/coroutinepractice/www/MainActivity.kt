package com.coroutinepractice.www

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch{ // will be destroyed when main activity gets destroyed
            delay(3000L) // 3 seconds (Only pauses the current coroutine)
            Log.d(TAG, "GlobalScope: Hello From ${Thread.currentThread().name}") // Dispatcher-worker
        }
        Log.d(TAG, "Main Thread: Hello from ${Thread.currentThread().name}") // Main Thread
    }
}