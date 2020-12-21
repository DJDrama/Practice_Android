package com.introducemyselfsample.www.ui.masterpiece

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.introducemyselfsample.www.data.Album
import com.introducemyselfsample.www.data.Idol
import com.introducemyselfsample.www.databinding.ItemLayoutAlbumBinding
import com.introducemyselfsample.www.databinding.ItemLayoutIdolsBinding


class IdolRecyclerViewAdapter(private val idolClickListener: IdolClickListener) :
    ListAdapter<Idol, IdolRecyclerViewAdapter.IdolViewHolder>(IDOL_COMPARATOR) {

    interface IdolClickListener {
        fun onIdolClicked(idol: Idol)
    }

    inner class IdolViewHolder(private val binding: ItemLayoutIdolsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(idol: Idol) {
            binding.textView6.text = idol.name
            binding.textView8.text = "${idol.number}명"
            binding.imageView5.setOnClickListener {
                idolClickListener.onIdolClicked(idol)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdolViewHolder {
        return IdolViewHolder(
            ItemLayoutIdolsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: IdolViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val IDOL_COMPARATOR = object : DiffUtil.ItemCallback<Idol>() {
            override fun areItemsTheSame(
                oldItem: Idol,
                newItem: Idol
            ): Boolean {
                return oldItem.idx == newItem.idx
            }

            override fun areContentsTheSame(
                oldItem: Idol,
                newItem: Idol
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}