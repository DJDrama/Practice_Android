package com.dj.sampleapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dj.sampleapp.R
import com.dj.sampleapp.data.model.PopularCard
import com.dj.sampleapp.data.model.PopularUser
import com.dj.sampleapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var popularCardsAdapter: PopularCardsAdapter
    private lateinit var popularUsersAdapter: PopularUsersAdapter
    private val viewModel: HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initRecyclerViews()
        subscribeObservers()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun initRecyclerViews() {
        binding.rvPopularCards.apply {
            popularCardsAdapter = PopularCardsAdapter(::onCardItemClick)
            itemAnimator = null
            adapter = popularCardsAdapter
        }
        binding.rvPopularUsers.apply {
            popularUsersAdapter = PopularUsersAdapter(::onPopularUserItemClick)
            itemAnimator = null
            adapter = popularUsersAdapter
        }
    }

    private fun onCardItemClick(popularCard: PopularCard) {
        val action = ContainerFragmentDirections.actionContainerFragmentToPopularCardDetailFragment(
            popularCard.id)
        findNavController().navigate(action)
    }

    private fun onPopularUserItemClick(popularUser: PopularUser) {
        val action =
            ContainerFragmentDirections.actionContainerFragmentToUserDetailFragment(popularUser.id)
        findNavController().navigate(action)
    }

    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                if (binding.swipeRefreshLayout.isRefreshing) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                when (it) {
                    is HomeViewModel.UiState.Success -> {
                        if(popularCardsAdapter.itemCount!=0)
                            popularCardsAdapter.submitList(null)
                        if(popularUsersAdapter.itemCount!=0)
                            popularUsersAdapter.submitList(null)

                        popularCardsAdapter.submitList(it.data["popular_cards"] as MutableList<PopularCard>)
                        popularUsersAdapter.submitList(it.data["popular_users"] as MutableList<PopularUser>)
                    }
                    is HomeViewModel.UiState.Error -> {

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