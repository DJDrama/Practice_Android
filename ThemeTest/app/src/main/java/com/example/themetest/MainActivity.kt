package com.example.themetest

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.themetest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let{
            when(it){
                binding.button->{
                    binding.constraintLayout.setBackgroundColor(Color.WHITE)
                    binding.text.setTextColor(Color.BLACK)
                }
                binding.button2->{
                    binding.constraintLayout.setBackgroundColor(Color.BLACK)
                    binding.text.setTextColor(Color.WHITE)
                }
                binding.button3->{
                    binding.constraintLayout.setBackgroundColor(Color.DKGRAY)
                    binding.text.setTextColor(Color.MAGENTA)
                }
            }
        }
    }
}