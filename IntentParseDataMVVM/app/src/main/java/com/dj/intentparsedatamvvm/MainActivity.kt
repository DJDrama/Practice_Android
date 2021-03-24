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

        val action = intent?.action
        val data = intent?.data

        val textView = findViewById<TextView>(R.id.tv_textview)
        textView.text = "$action $data ${data?.pathSegments} ${data?.host}"
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Toast.makeText(this, "Come here ?", Toast.LENGTH_LONG).show()
    }
}