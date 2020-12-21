package com.introducemyselfsample.www.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.introducemyselfsample.www.ui.album.MyAlbumActivity
import com.introducemyselfsample.www.databinding.ActivityMainBinding
import com.introducemyselfsample.www.ui.intro.AboutMySelfActivity
import com.introducemyselfsample.www.ui.masterpiece.MyMasterpieceActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            button.setOnClickListener(this@MainActivity)
            button2.setOnClickListener(this@MainActivity)
            button3.setOnClickListener(this@MainActivity)
            button4.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.button->{
                Intent(this, AboutMySelfActivity::class.java).also {
                    startActivity(it)
                }
            }
            binding.button2->{
                Intent(this, MyAlbumActivity::class.java).also {
                    startActivity(it)
                }
            }
            binding.button3->{
                Intent(this, MyMasterpieceActivity::class.java).also {
                    startActivity(it)
                }
            }
            binding.button4->{
                finishAffinity()
            }
        }
    }
}