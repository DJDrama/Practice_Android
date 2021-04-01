package com.dj.sampleapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dj.sampleapp.R
import com.dj.sampleapp.data.model.PopularCard
import com.dj.sampleapp.databinding.FragmentPhotoFeedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint

class PhotoFeedFragment : Fragment(R.layout.fragment_photo_feed) {
    private var _binding: FragmentPhotoFeedBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: PhotoFeedViewModel by viewModels()

    private lateinit var photoFeedAdapter: PhotoFeedAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPhotoFeedBinding.bind(view)
        initRecyclerView()
        subscribeToObservers()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerview.apply {
            photoFeedAdapter = PhotoFeedAdapter(::onCardItemClicked)
            itemAnimator = null
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val lm = layoutManager as LinearLayoutManager
                    val lastPosition = lm.findLastVisibleItemPosition()
                    if (lastPosition == photoFeedAdapter.itemCount - 1) {
                        viewModel.incrementPage()
                    }
                }
            })
            adapter = photoFeedAdapter

        }
    }

    private fun onCardItemClicked(popularCard: PopularCard) {
        val action = ContainerFragmentDirections.actionContainerFragmentToPopularCardDetailFragment(
            popularCard.id
        )
        findNavController().navigate(action)
    }


    private fun subscribeToObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                if (binding.swipeRefreshLayout.isRefreshing) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                when (it) {
                    is PhotoFeedViewModel.UiState.Success -> {
                        binding.tvReqeustFail.isVisible = false
                        val tempList = ArrayList(it.data)
                        photoFeedAdapter.submitList(tempList)
                    }
                    is PhotoFeedViewModel.UiState.Error -> {
                        binding.tvReqeustFail.isVisible = true
                    }
                    is PhotoFeedViewModel.UiState.Loading -> {

                    }
                    else -> {

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