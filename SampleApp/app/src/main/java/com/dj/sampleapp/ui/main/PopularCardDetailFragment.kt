package com.dj.sampleapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dj.sampleapp.R
import com.dj.sampleapp.data.model.PopularCard
import com.dj.sampleapp.data.model.PopularUser
import com.dj.sampleapp.databinding.FragmentPopularCardDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint

class PopularCardDetailFragment : Fragment(R.layout.fragment_popular_card_detail) {

    private val args by navArgs<PopularCardDetailFragmentArgs>()
    private val viewModel by viewModels<PopularCardDetailViewModel>()
    private var _binding: FragmentPopularCardDetailBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var popularCardsAdapter: PopularCardsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPopularCardDetailBinding.bind(view)
        viewModel.setCardId(args.cardId)
        initRecyclerView()
        subscribeToObservers()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerview.apply {
            popularCardsAdapter = PopularCardsAdapter(::onCardItemClicked)
            itemAnimator = null
            adapter = popularCardsAdapter
        }
    }

    private fun onCardItemClicked(popularCard: PopularCard) {
        val action =
            PopularCardDetailFragmentDirections.actionPopularCardDetailFragmentSelf(popularCard.id)
        findNavController().navigate(action)
    }

    private fun subscribeToObservers() {
        viewModel.cardId.observe(viewLifecycleOwner) {
            viewModel.fetchCardDetail(it)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                if (binding.swipeRefreshLayout.isRefreshing) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                when (it) {
                    is PopularCardDetailViewModel.UiState.Success -> {
                        val data = it.data

                        // card
                        val card = data["card"] as PopularCard
                        Glide.with(requireContext()).load(card.imgUrl).into(binding.imageView)
                        binding.textView2.text =
                            resources.getString(R.string.card_detail, card.description)

                        // user
                        val user = data["user"] as PopularUser
                        binding.tvAuthor.text = resources.getString(R.string.author, user.nickname)
                        binding.tvIntro.text =
                            resources.getString(R.string.intro_detail, user.introduction)

                        // Recommendation Cards
                        val recomCards = data["recommend_cards"] as List<PopularCard>
                        popularCardsAdapter.submitList(recomCards)
                    }
                    is PopularCardDetailViewModel.UiState.Error -> {

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