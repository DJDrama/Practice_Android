package com.dj.sampleapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dj.sampleapp.R
import com.dj.sampleapp.databinding.FragmentUserIntroBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_intro) {
    private var _binding: FragmentUserIntroBinding? = null
    private val binding
        get() = _binding!!

    private val args by navArgs<UserDetailFragmentArgs>()
    private val viewModel by viewModels<UserDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserIntroBinding.bind(view)

        viewModel.setUserId(args.userId)
        subscribeToObservers()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun subscribeToObservers() {
        viewModel.userId.observe(viewLifecycleOwner) {
            viewModel.fetchUserDetails()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                if (binding.swipeRefreshLayout.isRefreshing) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                when (it) {
                    is UserDetailViewModel.UiState.Success -> {
                        binding.tvReqeustFail.isVisible = false
                        binding.tvNickname.text =
                            resources.getString(R.string.nickname_detail, it.data.nickname)
                        binding.tvIntro.text =
                            resources.getString(R.string.intro_detail, it.data.introduction)
                    }
                    is UserDetailViewModel.UiState.Error -> {
                        binding.tvReqeustFail.isVisible = true
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