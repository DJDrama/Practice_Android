package com.movierecom.www.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.movierecom.www.databinding.ItemLayoutKeywordRankItemBinding
import com.movierecom.www.model.SearchKeyword

class RankAdapter : ListAdapter<String, RankAdapter.ViewHolder>(DiffUtil) {

    companion object DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
           return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(private val binding: ItemLayoutKeywordRankItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(searchKeyword: String){
            binding.apply {
                tvRank.text = "${adapterPosition+1}"
                tvTitle.text = searchKeyword
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutKeywordRankItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}