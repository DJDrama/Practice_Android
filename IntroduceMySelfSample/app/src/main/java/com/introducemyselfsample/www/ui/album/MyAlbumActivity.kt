package com.introducemyselfsample.www.ui.album

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.introducemyselfsample.www.data.Album
import com.introducemyselfsample.www.data.getAlbums
import com.introducemyselfsample.www.databinding.ActivityMyAlbumBinding


class MyAlbumActivity : AppCompatActivity(), RecyclerViewAdapter.AlbumClickListener {
    private lateinit var binding: ActivityMyAlbumBinding
    private lateinit var mAdapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        getAlbumList()
        binding.button5.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            mAdapter = RecyclerViewAdapter(this@MyAlbumActivity)
            adapter = mAdapter
        }
    }

    private fun getAlbumList() {
        val list = getAlbums()
        showAlbum(list[0])
        mAdapter.submitList(list)
    }

    private fun showAlbum(album: Album) {
        binding.apply {
            imageView3.setImageResource(album.img)
            imageView4.setImageResource(album.img)
            tvAlbumTitle.text = album.title
        }
    }

    override fun onAlbumClicked(album: Album) {
        showAlbum(album)
    }
}
