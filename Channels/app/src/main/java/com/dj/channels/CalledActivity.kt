package com.dj.channels

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CalledActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_called)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener{
            setResult(RESULT_OK)
            finish()
        }
    }

}