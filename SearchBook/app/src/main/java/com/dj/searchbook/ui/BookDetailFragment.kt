package com.dj.searchbook.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.dj.searchbook.R
import com.dj.searchbook.databinding.FragmentBookDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dj.searchbook.util.DateUtils
import java.util.*

@AndroidEntryPoint
class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {
    private var _binding: FragmentBookDetailBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: BookViewModel by activityViewModels()
    private val args: BookDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookDetailBinding.bind(view)

        args.document?.let {
            viewModel.setDetailDocument(it)
        }
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.detailDocument.observe(viewLifecycleOwner) {
            it?.let { document ->
                binding.apply {
                    Glide.with(requireContext()).asBitmap()
                        .load(document.thumbNail)
                        .placeholder(R.drawable.no_image)
                        .into(object: CustomTarget<Bitmap>(){
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?,
                            ) {
                                ivThumbnail.setImageBitmap(resource)
                                Palette.from(resource).generate{palette->
                                    val color = palette?.lightMutedSwatch?.rgb ?: 0
                                    frameLayout.setBackgroundColor(color)
                                }
                            }

                            override fun onLoadFailed(errorDrawable: Drawable?) {
                                super.onLoadFailed(errorDrawable)
                                ivThumbnail.setImageResource(R.drawable.no_image)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                            }
                        })
                    tvTitle.text = document.title
                    tvAuthors.text = document.authors.joinToString()
                    tvPublisher.text = document.publisher
                    tvDesc.text = document.contents
                    tvDate.text = "출판일: ${DateUtils.dateToString(document.dateTime)}"


                    ivFavorite.setImageResource(
                        if (document.isFavorite) R.drawable.ic_baseline_favorite_24
                        else R.drawable.ic_baseline_favorite_border_24
                    )
                    ivFavorite.setOnClickListener {
                        viewModel.setFavorite(document)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}