package com.coroutinepractice.www

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.coroutinepractice.www.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val job = GlobalScope.launch(Default){
            Log.d(TAG, "Starting long running calculation...")
            withTimeout(300L){ // we can use this  as job.cancel()
                for(i in 30..40){
                    if(isActive) { // use it for cancellation
                        val result = fib(i)
                        Log.d(TAG, "Result for i = $i: $result")
                    }
                }
                Log.d(TAG, "Ending long running calculator...")
            }
//            repeat(5){
//                Log.d(TAG, "Coroutine is still working...")
//                delay(1000L)
//            }
        }
//
//        runBlocking {
//            delay(500L)
//            job.cancel() // after 2 seconds cancels the job.
//            //job.join() // blocks the main thread until this coroutine gets finished
//           // Log.d(TAG, "Main Thread is continuing...")
//            Log.d(TAG, "Canceled job!")
//        }
    }

    fun fib(n: Int): Long{
        if(n==0) return 0
        if(n==1) return 1
        return fib(n-1)+fib(n-2)
    }

}