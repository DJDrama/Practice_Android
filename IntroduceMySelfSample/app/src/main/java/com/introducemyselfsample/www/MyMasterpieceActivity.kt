package com.introducemyselfsample.www

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.introducemyselfsample.www.databinding.ActivityMyAlbumBinding
import com.introducemyselfsample.www.databinding.ActivityMyMasterpeieceBinding

class MyMasterpieceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyMasterpeieceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMasterpeieceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
