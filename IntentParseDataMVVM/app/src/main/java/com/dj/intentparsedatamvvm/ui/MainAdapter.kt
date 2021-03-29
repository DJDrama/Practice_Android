package com.dj.intentparsedatamvvm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.dj.intentparsedatamvvm.data.ListItemHolder
import com.dj.intentparsedatamvvm.databinding.ItemCardLayoutBinding
import com.dj.intentparsedatamvvm.databinding.ItemImageLayoutBinding


class MainAdapter : ListAdapter<ListItemHolder, ListItemHolderViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolderViewHolder {
        return ListItemHolderViewHolder(
            if (viewType == ITEM)
                ItemCardLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            else
                ItemImageLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
        )
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item.image?.let {
            IMAGE
        } ?: ITEM
    }

    override fun onBindViewHolder(holder: ListItemHolderViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    companion object {
        const val ITEM = 0
        const val IMAGE = 1
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItemHolder>() {
            override fun areItemsTheSame(
                oldItem: ListItemHolder,
                newItem: ListItemHolder
            ): Boolean {
                return oldItem.issue?.number == newItem.issue?.number
            }

            override fun areContentsTheSame(
                oldItem: ListItemHolder,
                newItem: ListItemHolder
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class ListItemHolderViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(listItemHolder: ListItemHolder) {
        if (binding is ItemCardLayoutBinding)
            binding.tvDesc.text = "#${listItemHolder.issue?.number} ${listItemHolder.issue?.title}"
        else if (binding is ItemImageLayoutBinding)
            Glide.with(binding.root.context).load(listItemHolder.image).into(binding.ivImage)
    }
}