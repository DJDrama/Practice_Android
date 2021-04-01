package com.dj.sampleapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dj.sampleapp.data.model.PopularUser
import com.dj.sampleapp.databinding.ItemPopularUserLayoutBinding

class PopularUsersAdapter(private val onClick: (PopularUser) -> Unit) :
    ListAdapter<PopularUser, PopularUsersAdapter.PopularUserViewHolder>(DIFF_CALLBACK) {
    companion object DIFF_CALLBACK : DiffUtil.ItemCallback<PopularUser>() {
        override fun areItemsTheSame(oldItem: PopularUser, newItem: PopularUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PopularUser, newItem: PopularUser): Boolean {
            return oldItem == newItem
        }
    }

    class PopularUserViewHolder(
        private val binding: ItemPopularUserLayoutBinding,
        private val onClick: (PopularUser) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var popularUser: PopularUser? = null

        init {
            binding.root.setOnClickListener {
                popularUser?.let {
                    onClick(it)
                }
            }
        }

        fun bind(popularUser: PopularUser) {
            this.popularUser = popularUser
            binding.tvTitle.text = popularUser.nickname
            binding.tvIntro.text = popularUser.introduction
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularUserViewHolder {
        return PopularUserViewHolder(
            ItemPopularUserLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClick
        )
    }

    override fun onBindViewHolder(holder: PopularUserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
