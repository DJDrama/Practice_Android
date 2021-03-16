package com.movierecom.www.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.movierecom.www.R
import com.movierecom.www.databinding.FragmentBoxOfficeDetailBinding
import com.movierecom.www.model.DailyBoxOffice
import com.movierecom.www.util.getCommaString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoxOfficeDetailFragment : Fragment(R.layout.fragment_box_office_detail) {
    private var _binding: FragmentBoxOfficeDetailBinding? = null
    private val binding
        get() = _binding!!

    private val args: BoxOfficeDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBoxOfficeDetailBinding.bind(view)

        val boxOfficeItem = args.boxOfficeItem
        binding.apply {
            tvTitle.text = boxOfficeItem.movieNm
            tvOpenDate.text = "개봉일 : ${boxOfficeItem.openDt}"
            tvSalesAcc.text = "누적 매출액: ${boxOfficeItem.salesAcc.getCommaString()}원"
            tvAudiAcc.text = "누적 관객수: ${boxOfficeItem.audiAcc.getCommaString()}명"

            args.boxOfficeItem.naverMovie?.let {
                tvDirectors.text = "감독: ${it.director.split("|").joinToString()}"
                tvActors.text = "배우: ${it.actor.split("|").joinToString()}"
                ratingBar.progress = it.userRating.toInt()
            }

            Glide.with(this@BoxOfficeDetailFragment).asBitmap()
                .load(boxOfficeItem.naverMovie?.image)
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