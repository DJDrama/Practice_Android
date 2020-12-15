package com.answer.univ.ui.launcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().hideActionBar()
        _binding = FragmentLoginBinding.bind(view)
        binding.apply {
            buttonLogin.setOnClickListener(this@LoginFragment)
            tvRegister.setOnClickListener(this@LoginFragment)
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
        lifecycleScope.launch(IO) {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                withContext(Main) {
                    checkCurrentUser()
                }
            } catch (e: Exception) {
                withContext(Main) {
                    when (e) {
                        is FirebaseAuthInvalidUserException -> {
                            binding.root.showSnackBar("일치하는 회원이 없습니다.")
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            binding.root.showSnackBar("이메일 비밀번호를 확인해주세요.")
                        }
                    }
                }
            }
        }
    }

    private fun checkCurrentUser() {
        if (firebaseAuth.currentUser != null) {
            Intent(requireActivity(), MainActivity::class.java).also {
                startActivity(it)
            }
            requireActivity().finish()
        } else {
            binding.root.showSnackBar("알 수 없는 에러. 다시 시도해주세요.")
        }
    }
}