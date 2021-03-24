package com.dj.intentparsedatamvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleData(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleData(intent)
    }

    private fun handleData(intent: Intent?){
        intent?.let{
            val data = it.data
            val textView = findViewById<TextView>(R.id.tv_textview)
            textView.text = "${data?.host} ${data?.pathSegments?.get(0)}"
        }
    }
}