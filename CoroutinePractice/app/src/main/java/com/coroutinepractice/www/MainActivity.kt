package com.coroutinepractice.www

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.coroutinepractice.www.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Before runBlocking")
        runBlocking { //blocks the main thread
            launch(IO) {
                delay(3000L)
                Log.d(TAG, "Finished IO Coroutine 1")
            }

            Log.d(TAG, "Start of runBlocking")
            delay(3000L)  // same as Thread.sleep(5000L) if inside of runBlocking
            Log.d(TAG, "End of runBlocking")

            launch(IO) {
                delay(3000L)
                Log.d(TAG, "Finished IO Coroutine 2")
            }
        }
        Log.d(TAG, "After runBlocking")

    }

}