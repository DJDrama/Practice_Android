package com.movierecom.www.ui

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        binding.cardView2.setOnClickListener(this)
        binding.editText.setOnClickListener(this)

        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        dailyBoxOfficeAdapter = DailyBoxOfficeAdapter(this::onBoxOfficeItemClicked)
        binding.rvDailyBoxOffice.apply{
            adapter = dailyBoxOfficeAdapter
        }
    }

    private fun onBoxOfficeItemClicked(dailyBoxOffice: DailyBoxOffice){
        val action = MainFragmentDirections.actionMainFragmentToBoxOfficeDetailFragment(dailyBoxOffice)
        findNavController().navigate(action)
    }

    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dailyBoxOfficeList.collect {
                dailyBoxOfficeAdapter.submitList(it)
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