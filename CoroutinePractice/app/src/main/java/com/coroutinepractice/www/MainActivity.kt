package com.coroutinepractice.www

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.coroutinepractice.www.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch(IO) {
            Log.d(TAG, "Starting coroutine in thread ${Thread.currentThread().name}")
            val answer = doNetworkCall()
            withContext(Main){ // Switch Context
                Log.d(TAG, "Setting TextView in thread ${Thread.currentThread().name}")
                binding.textView.text = answer
            }
        }
    }

    suspend fun doNetworkCall(): String {
        delay(3000L)
        return "This is the answer"
    }

}