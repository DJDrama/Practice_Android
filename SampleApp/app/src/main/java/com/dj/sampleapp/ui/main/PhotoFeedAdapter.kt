package com.dj.sampleapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dj.sampleapp.data.model.PopularCard
import com.dj.sampleapp.databinding.ItemFeedCardLayoutBinding

class PhotoFeedAdapter(private val onItemClickListener: (PopularCard) -> Unit) :
    ListAdapter<PopularCard, PhotoFeedAdapter.PopularCardViewHolder>(DIFF_CALLBACK) {

    companion object DIFF_CALLBACK : DiffUtil.ItemCallback<PopularCard>() {
        override fun areItemsTheSame(oldItem: PopularCard, newItem: PopularCard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PopularCard, newItem: PopularCard): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class PopularCardViewHolder(
        private val binding: ItemFeedCardLayoutBinding,
        private val onItemClickListener: (PopularCard) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var popularCard: PopularCard? = null

        init {
            binding.root.setOnClickListener {
                this.popularCard?.let {
                    onItemClickListener(it)
                }
            }
        }

        fun bind(popularCard: PopularCard) {
            this.popularCard = popularCard
            Glide.with(binding.root.context).load(popularCard.imgUrl).into(binding.imageView)
            binding.textView2.text = popularCard.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCardViewHolder {
        return PopularCardViewHolder(
            ItemFeedCardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: PopularCardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
