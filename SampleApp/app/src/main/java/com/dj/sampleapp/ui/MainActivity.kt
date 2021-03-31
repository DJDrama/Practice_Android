package com.dj.sampleapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.dj.sampleapp.R
import com.dj.sampleapp.databinding.ActivityMainBinding
import com.dj.sampleapp.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment).navController
    }
    private val viewModel by viewModels<AuthViewModel>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonLogin.setOnClickListener(this@MainActivity)
            buttonRegister.setOnClickListener(this@MainActivity)
            buttonLogout.setOnClickListener(this@MainActivity)
        }


        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it) {
                    is AuthViewModel.UiState.Success -> {
                        binding.buttonLogin.isVisible = false
                        binding.buttonRegister.isVisible = false
                        binding.buttonLogout.isVisible = true
                    }
                    else -> {

                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.buttonLogin -> {
                navController.navigate(R.id.action_global_loginFragment)
            }
            binding.buttonRegister -> {
                navController.navigate(R.id.action_global_registerFragment2)
            }
            binding.buttonLogout -> {
                // logout
                binding.apply {
                    buttonLogin.isVisible = true
                    buttonRegister.isVisible = true
                    buttonLogout.isVisible = false
                }
            }
        }
    }
}