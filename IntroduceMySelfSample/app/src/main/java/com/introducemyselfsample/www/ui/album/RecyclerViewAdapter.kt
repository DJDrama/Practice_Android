package com.introducemyselfsample.www.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.introducemyselfsample.www.data.Album
import com.introducemyselfsample.www.databinding.ItemLayoutAlbumBinding


class RecyclerViewAdapter(private val albumClickListener: AlbumClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.AlbumViewHolder>() {

    interface AlbumClickListener {
        fun onAlbumClicked(album: Album)
    }

    private val list = mutableListOf<Album>()

    fun submitList(list: List<Album>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class AlbumViewHolder(private val binding: ItemLayoutAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = list[position]
                    albumClickListener.onAlbumClicked(item)
                }
            }
        }

        fun bind(album: Album) {
            binding.imageView2.setImageResource(album.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            ItemLayoutAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(this.list[position])
    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}