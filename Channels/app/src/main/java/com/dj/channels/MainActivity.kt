package com.dj.channels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            mainViewModel.setInt(10) // channel

            mainViewModel.setIntForSharedFlow(10)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            Intent(this, CalledActivity::class.java).also{
                resultLauncher.launch(it)
            }
        }

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch{
                    mainViewModel.channelFlow.collect {
                        println("Collected Value: $it")
                    }
                }
                launch{
                    mainViewModel.sharedFlow.collect {
                        println("SharedFlow Collected Value: $it")
                    }
                }
            }
        }
        mainViewModel.setInt(5)
    }
}