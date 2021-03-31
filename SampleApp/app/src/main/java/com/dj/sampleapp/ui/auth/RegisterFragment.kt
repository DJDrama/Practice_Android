package com.dj.sampleapp.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dj.sampleapp.R
import com.dj.sampleapp.databinding.FragmentLoginBinding
import com.dj.sampleapp.databinding.FragmentRegisterBinding
import com.dj.sampleapp.ui.showToast
import kotlinx.coroutines.flow.collect

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        subscribeToObservers()
        binding.btnLogin.setOnClickListener {
            viewModel.register(
                nickname = binding.edtNickname.text.toString(),
                pwd = binding.edtPassword.text.toString(),
                introduction = binding.edtIntro.text.toString()
            )
        }
    }

    private fun subscribeToObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it) {
                    is AuthViewModel.UiState.Success -> {
                        findNavController().popBackStack()
                    }
                    is AuthViewModel.UiState.Error -> {
                        requireContext().showToast(it.errorMessage)
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