package com.answer.univ.ui.launcher

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.answer.univ.R
import com.answer.univ.databinding.FragmentRegisterBinding
import com.answer.univ.ui.showActionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register), View.OnClickListener {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        requireActivity().showActionBar()
        binding.btnRegister.setOnClickListener(this)
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.universities.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                it.toList()
            )
            binding.spinnerUniversities.adapter = adapter
        }
        viewModel.interests.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                it
            )
            binding.spinnerInterest.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        if(p0 == binding.btnRegister){

        }
    }
}