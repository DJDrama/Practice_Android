package com.answer.univ.ui.launcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.answer.univ.R
import com.answer.univ.ui.MainActivity
import com.answer.univ.ui.hideActionBar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class IntroSplashFragment : Fragment(R.layout.fragment_intro_splash) {
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().hideActionBar()

        lifecycleScope.launch {
            delay(1000)
            checkLoginStatus()
        }
    }

    private fun checkLoginStatus() {
        firebaseAuth.currentUser?.let {
            Intent(requireActivity(), MainActivity::class.java).also {
                startActivity(it)
            }
            requireActivity().finish()
        } ?: findNavController().navigate(R.id.action_introSplashFragment_to_loginFragment)

    }
}