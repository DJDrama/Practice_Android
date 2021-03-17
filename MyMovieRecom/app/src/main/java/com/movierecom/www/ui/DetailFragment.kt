package com.movierecom.www.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.movierecom.www.R
import com.movierecom.www.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val args by navArgs<DetailFragmentArgs>()
    private var _binding: FragmentDetailBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        binding.apply {
            tvTitle.text = args.naverMovie.title
            tvOpenDate.text = "개봉년도 : ${args.naverMovie.pubDate}"

            tvDirectors.text = "감독: ${args.naverMovie.director.split("|").joinToString()}"
            tvActors.text = "배우: ${args.naverMovie.actor.split("|").joinToString()}"
            ratingBar.progress = args.naverMovie.userRating.toInt()

            Glide.with(this@DetailFragment).asBitmap()
                .load(args.naverMovie.image)
                .placeholder(R.drawable.no_image)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?,
                    ) {
                        imageView3.setImageBitmap(resource)
                        Palette.from(resource).generate { palette ->
                            val color = palette?.lightMutedSwatch?.rgb ?: 0
                            frameLayout.setBackgroundColor(color)
                        }
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        imageView3.setImageResource(R.drawable.no_image)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}