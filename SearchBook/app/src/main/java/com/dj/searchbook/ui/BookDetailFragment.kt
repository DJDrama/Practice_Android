package com.dj.searchbook.ui

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dj.searchbook.R
import com.dj.searchbook.databinding.FragmentBookDetailBinding
import com.dj.searchbook.util.DateUtils
import com.dj.searchbook.util.getNumberFormattedString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {
    private var _binding: FragmentBookDetailBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: BookDetailViewModel by viewModels()
    private val args: BookDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookDetailBinding.bind(view)
        setHasOptionsMenu(true)
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
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?,
                            ) {
                                ivThumbnail.setImageBitmap(resource)
                                Palette.from(resource).generate { palette ->
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
                    tvAuthors.text = getString(R.string.author, document.authors.joinToString())
                    tvPublisher.text = getString(R.string.publisher, document.publisher)
                    tvDesc.text = document.contents
                    tvDate.text = getString(
                        R.string.release,
                        document.dateTime?.let { date ->
                            DateUtils.dateToString(date)
                        } ?: "N/A"
                    )
                    tvTranslators.text = getString(
                        R.string.translators,
                        if (document.translators.isEmpty()) "None"
                        else document.translators.joinToString()
                    )

                    tvIsbn.text = getString(R.string.isbn, document.isbn)
                    tvPrice.text =
                        getString(R.string.price, getNumberFormattedString(document.price))
                    if (document.salePrice == -1) {
                        tvSalePrice.isVisible = false
                    } else {
                        tvSalePrice.text =
                            getString(R.string.price, getNumberFormattedString(document.salePrice))
                        tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    tvUrl.text = getString(R.string.link, document.url)

                    requireActivity().invalidateOptionsMenu()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.favorite_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (viewModel.getIsFavorite())
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_baseline_favorite_24)
        else
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_baseline_favorite_border_24)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                viewModel.setFavorite()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
