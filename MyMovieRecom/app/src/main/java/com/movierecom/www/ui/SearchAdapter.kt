package com.movierecom.www.ui

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movierecom.www.R
import com.movierecom.www.databinding.ItemLayoutSearchItemBinding
import com.movierecom.www.model.NaverMovie

class SearchAdapter(private val onClick: (NaverMovie) -> Unit) :
    ListAdapter<NaverMovie, SearchAdapter.ViewHolder>(DiffUtil) {
    companion object DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<NaverMovie>() {
        override fun areItemsTheSame(oldItem: NaverMovie, newItem: NaverMovie): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: NaverMovie, newItem: NaverMovie): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(
        private val binding: ItemLayoutSearchItemBinding,
        private val onClick: (NaverMovie) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init{
            binding.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(naverMovie: NaverMovie) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(naverMovie.image)
                    .placeholder(R.drawable.no_image)
                    .into(imageView4)
                tvTitle.text = HtmlCompat.fromHtml(naverMovie.title, Html.FROM_HTML_MODE_LEGACY).toString()
                tvYear.text = "개봉년도: ${naverMovie.pubDate}"
                tvActors.text = "배우: ${naverMovie.actor.split("|").joinToString()}"
                tvDirectors.text = "감독: ${naverMovie.director.split("|").joinToString()}"
                ratingBar2.progress = naverMovie.userRating.toInt()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutSearchItemBinding.inflate(
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
