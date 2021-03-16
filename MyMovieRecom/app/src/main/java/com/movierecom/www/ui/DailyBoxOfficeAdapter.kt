package com.movierecom.www.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movierecom.www.R
import com.movierecom.www.databinding.ItemLayoutBoxOfficeBinding
import com.movierecom.www.model.DailyBoxOffice

class DailyBoxOfficeAdapter(private val onClick: (DailyBoxOffice) -> Unit) :
    ListAdapter<DailyBoxOffice, DailyBoxOfficeAdapter.ViewHolder>(DiffUtil) {

    companion object DiffUtil :
        androidx.recyclerview.widget.DiffUtil.ItemCallback<DailyBoxOffice>() {
        override fun areItemsTheSame(oldItem: DailyBoxOffice, newItem: DailyBoxOffice): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DailyBoxOffice, newItem: DailyBoxOffice): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(
        private val binding: ItemLayoutBoxOfficeBinding,
        private val onClick: (DailyBoxOffice) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }

        fun bind(dailyBoxOffice: DailyBoxOffice) {
            binding.apply {
                Glide.with(root.context).load(dailyBoxOffice.naverMovie?.image)
                    .placeholder(R.drawable.no_image).into(imageView2)
                tvTitle.text = dailyBoxOffice.movieNm
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutBoxOfficeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}