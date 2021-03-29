package com.dj.intentparsedatamvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dj.intentparsedatamvvm.databinding.ActivityMainBinding

// adb shell am start -a android.intent.action.VIEW -d issues://JakeWharton/hugo/issues

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Open Issues"

        initRecyclerView()
        handleData(intent)
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.list.observe(this) {
            mainAdapter.submitList(it)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerview.apply {
            mainAdapter = MainAdapter()
            adapter = mainAdapter
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleData(intent)
    }

    private fun handleData(intent: Intent?) {
        intent?.let {
            it.data?.let { data ->
                val username = data.host
                val repositoryName = data.pathSegments[0]
                binding.tvTitle.text = "$username / $repositoryName"
                viewModel.fetchIssues(
                    username = username ?: "",
                    repositoryName = repositoryName
                )
            }
        }
    }
}