package com.movierecom.www.ui

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.EditorInfo.IME_ACTION_UNSPECIFIED
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.movierecom.www.R
import com.movierecom.www.databinding.FragmentSearchBinding
import com.movierecom.www.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        setHasOptionsMenu(true)
        initRecyclerView()
        subscribeObservers()
    }


    private fun initRecyclerView() {
        searchAdapter = SearchAdapter()
        binding.recyclerview.apply {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), VERTICAL)
            addItemDecoration(dividerItemDecoration)
            adapter = searchAdapter
        }
    }

    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieList.collect {
                searchAdapter.submitList(null)
                searchAdapter.submitList(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        requireActivity().apply {
            val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
            val searchView = menu.findItem(R.id.action_search).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.isSubmitButtonEnabled = true
            searchView.onActionViewExpanded() // Always Expand
            searchView.queryHint = "검색..."
            val searchEdt = searchView.findViewById(R.id.search_src_text) as EditText
            searchEdt.setOnEditorActionListener { v, actionId, event ->
                if (actionId == IME_ACTION_SEARCH || actionId == IME_ACTION_UNSPECIFIED) {
                    val query = v.text.toString()
                    requireContext().hideKeyboard(v)
                    viewModel.searchQuery(query)
                }
                true
            }
            val searchBtn = searchView.findViewById(R.id.search_go_btn) as View
            searchBtn.setOnClickListener {
                val query = searchEdt.text.toString()
                requireContext().hideKeyboard(it)
                viewModel.searchQuery(query)
            }
        }
    }
}