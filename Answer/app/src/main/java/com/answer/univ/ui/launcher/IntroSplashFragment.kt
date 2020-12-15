package com.answer.univ.ui.launcher

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.answer.univ.R
import com.answer.univ.ui.hideActionBar
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IntroSplashFragment: Fragment(R.layout.fragment_intro_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().hideActionBar()

        lifecycleScope.launch(Main){
            delay(1000)
            findNavController().navigate(R.id.loginFragment)
        }
    }
}