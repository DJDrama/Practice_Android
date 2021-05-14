package com.movierecom.www.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoxOfficeDetailFragment : Fragment(R.layout.fragment_box_office_detail) {
    private var _binding: FragmentBoxOfficeDetailBinding? = null
    private val binding
        get() = _binding!!

    private val args: BoxOfficeDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailViewModel>()


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
        viewModel.getTrailer(query = boxOfficeItem.movieNm)

        subscribeToObservers()

    }
    private fun subscribeToObservers(){
        viewModel.trailer.observe(viewLifecycleOwner){
            binding.youtubePlayerView.addYouTubePlayerListener(object: YouTubePlayerListener {
                override fun onApiChange(youTubePlayer: YouTubePlayer) {
                }

                override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                }

                override fun onError(
                    youTubePlayer: YouTubePlayer,
                    error: PlayerConstants.PlayerError,
                ) {
                }

                override fun onPlaybackQualityChange(
                    youTubePlayer: YouTubePlayer,
                    playbackQuality: PlayerConstants.PlaybackQuality,
                ) {
                }

                override fun onPlaybackRateChange(
                    youTubePlayer: YouTubePlayer,
                    playbackRate: PlayerConstants.PlaybackRate,
                ) {
                }

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(it, 0f)
                    //youTubePlayer.setVolume(100)
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState,
                ) {
                }

                override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                }

                override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                }

                override fun onVideoLoadedFraction(
                    youTubePlayer: YouTubePlayer,
                    loadedFraction: Float,
                ) {
                }
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}