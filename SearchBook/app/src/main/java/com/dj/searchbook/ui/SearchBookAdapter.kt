package com.dj.searchbook.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dj.searchbook.R
import com.dj.searchbook.data.model.Document
import com.dj.searchbook.databinding.ItemLayoutDocumentBinding
import com.dj.searchbook.util.DateUtils

class SearchBookAdapter(private val onItemClicked: (Document) -> Unit) :
    ListAdapter<Document, SearchBookAdapter.ViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Document>() {
        override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
            return oldItem.isbn == newItem.isbn
        }

        override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(
        private val binding: ItemLayoutDocumentBinding,
        private val onItemClicked: (Document) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked(getItem(adapterPosition))
            }
        }

        fun bind(document: Document) {
            binding.apply {
                tvName.text = document.title
                tvDesc.text = document.contents
                tvDate.text = DateUtils.dateToString(document.dateTime)
                if (document.salePrice == -1) {
                    tvSalePrice.text = "${document.price}원"
                    tvPrice.visibility = View.INVISIBLE
                } else {
                    tvPrice.isVisible = true
                    tvSalePrice.text = "${document.salePrice}원"
                    tvPrice.text = "${document.price}원"
                    tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }

                if (document.isFavorite)
                    ivFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                else
                    ivFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)

                Glide.with(root.context)
                    .load(document.thumbNail)
                    .placeholder(R.drawable.no_image)
                    .into(ivThumbnail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutDocumentBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false), onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}