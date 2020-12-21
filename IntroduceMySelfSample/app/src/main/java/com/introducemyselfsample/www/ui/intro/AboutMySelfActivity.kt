package com.introducemyselfsample.www.ui.intro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.introducemyselfsample.www.databinding.ActivityAboutMySelfBinding

class AboutMySelfActivity : AppCompatActivity(){
    private lateinit var binding: ActivityAboutMySelfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutMySelfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button5.setOnClickListener {
            finish()
        }
    }

}
