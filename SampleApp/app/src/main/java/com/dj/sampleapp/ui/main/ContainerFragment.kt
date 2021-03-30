package com.dj.sampleapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dj.sampleapp.R
import com.dj.sampleapp.databinding.FragmentContainerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ContainerFragment : Fragment(R.layout.fragment_container) {
    private var _binding: FragmentContainerBinding? = null
    private val binding: FragmentContainerBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentContainerBinding.bind(view)

        initViewPager()
    }

    private fun initViewPager() {
        binding.viewPager.apply {
            adapter = HomePagerAdapter(childFragmentManager)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}