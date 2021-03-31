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
import com.dj.sampleapp.ui.showToast
import kotlinx.coroutines.flow.collect

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        subscribeToObservers()
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                nickname = binding.edtNickname.text.toString(),
                pwd = binding.edtPassword.text.toString()
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