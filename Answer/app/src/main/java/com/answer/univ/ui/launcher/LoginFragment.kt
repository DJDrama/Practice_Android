package com.answer.univ.ui.launcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.answer.univ.R
import com.answer.univ.databinding.FragmentLoginBinding
import com.answer.univ.ui.MainActivity
import com.answer.univ.ui.hideActionBar
import com.answer.univ.ui.showSnackBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().hideActionBar()
        _binding = FragmentLoginBinding.bind(view)
        binding.apply {
            buttonLogin.setOnClickListener(this@LoginFragment)
            tvRegister.setOnClickListener(this@LoginFragment)
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.error.observe(viewLifecycleOwner) {
            binding.root.showSnackBar(it)
        }
        viewModel.loginSuccess.observe(viewLifecycleOwner) {
            if (it) {
                Intent(requireActivity(), MainActivity::class.java).also { intent ->
                    startActivity(intent)
                }
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.buttonLogin -> {
                login()
            }
            binding.tvRegister -> {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
    }

    private fun login() {
        val email = binding.textInputEditText.text.toString()
        val password = binding.textInputPassword.text.toString()
        if (email.isEmpty()) {
            binding.root.showSnackBar("이메일 주소를 입력해주세요.")
            return
        }
        if (password.isEmpty()) {
            binding.root.showSnackBar("비밀번호를 입력해주세요.")
        }
        viewModel.login(email, password)
    }


}