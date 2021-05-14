package com.movierecom.www.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.movierecom.www.R
import com.movierecom.www.databinding.FragmentDetailBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import androidx.annotation.NonNull
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val args by navArgs<DetailFragmentArgs>()
    private var _binding: FragmentDetailBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        binding.apply {
            tvTitle.text = HtmlCompat.fromHtml(args.naverMovie.title, Html.FROM_HTML_MODE_LEGACY).toString()
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
        viewModel.getTrailer(query = args.naverMovie.title)

        subscribeToObservers()
    }
    private fun subscribeToObservers(){
        viewModel.trailer.observe(viewLifecycleOwner){
            binding.youtubePlayerView.addYouTubePlayerListener(object: YouTubePlayerListener{
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