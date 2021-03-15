package com.movierecom.www.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.movierecom.www.R
import com.movierecom.www.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main), View.OnClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        binding.cardView2.setOnClickListener(this)
        binding.editText.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onClick(v: View?) {
        when(v){
            binding.cardView2, binding.editText->{
                findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
            }
        }
    }
}