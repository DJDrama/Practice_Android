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

        GlobalScope.launch {
            delay(1000L) // suspend function (only called inside a suspend fun or from a coroutine)
            val networkCallAnswer = doNetworkCall()
            val networkCallAnswer2 = doNetworkCall2()

            // since two methods are in a same coroutine, despite of different functions,
            // delay(3000L) will be add up to delay(6000L)
            Log.d(TAG, networkCallAnswer)
            Log.d(TAG, networkCallAnswer2)

        }
    }

    suspend fun doNetworkCall(): String {
        delay(3000L)
        return "This is the answer"
    }

    suspend fun doNetworkCall2(): String {
        delay(3000L)
        return "This is the answer2"
    }
}