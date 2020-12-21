package com.introducemyselfsample.www

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.introducemyselfsample.www.databinding.ActivityAboutMySelfBinding
import com.introducemyselfsample.www.databinding.ActivityMyAlbumBinding

class MyAlbumActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMyAlbumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
