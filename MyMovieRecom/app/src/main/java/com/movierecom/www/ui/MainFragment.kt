package com.movierecom.www.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.movierecom.www.R
import com.movierecom.www.databinding.FragmentMainBinding
import com.movierecom.www.model.DailyBoxOffice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), View.OnClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private val viewModel by viewModels<MainFragmentViewModel>()
    private lateinit var dailyBoxOfficeAdapter: DailyBoxOfficeAdapter
    private lateinit var rankAdapter: RankAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        binding.cardView2.setOnClickListener(this)
        binding.editText.setOnClickListener(this)
        viewModel.fetchQueryRank()
        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        dailyBoxOfficeAdapter = DailyBoxOfficeAdapter(this::onBoxOfficeItemClicked)
        binding.rvDailyBoxOffice.apply {
            adapter = dailyBoxOfficeAdapter
        }
        rankAdapter = RankAdapter(::onRankItemClick)
        binding.rvKeywordRank.apply {
            adapter = rankAdapter
        }
    }

    private fun onRankItemClick(query: String){
        val action = MainFragmentDirections.actionMainFragmentToSearchFragment(query = query)
        findNavController().navigate(action)
    }

    private fun onBoxOfficeItemClicked(dailyBoxOffice: DailyBoxOffice) {
        val action =
            MainFragmentDirections.actionMainFragmentToBoxOfficeDetailFragment(dailyBoxOffice)
        findNavController().navigate(action)
    }

    private fun subscribeObservers() {
        viewModel.dailyBoxOfficeList.observe(viewLifecycleOwner) {
            dailyBoxOfficeAdapter.submitList(it)
        }
        viewModel.keywordList.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                binding.tvNoRankItems.isVisible=false
                binding.rvKeywordRank.isVisible=true
                rankAdapter.submitList(null)
                rankAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.cardView2, binding.editText -> {
                findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
            }
        }
    }
}