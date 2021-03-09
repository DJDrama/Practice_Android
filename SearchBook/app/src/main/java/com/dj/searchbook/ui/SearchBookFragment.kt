package com.dj.searchbook.ui

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.EditorInfo.IME_ACTION_UNSPECIFIED
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dj.searchbook.R
import com.dj.searchbook.data.model.Document
import com.dj.searchbook.databinding.FragmentSearchBookBinding
import com.dj.searchbook.util.NO_DATA
import com.dj.searchbook.util.hideKeyboard
import com.dj.searchbook.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchBookFragment : Fragment(R.layout.fragment_search_book) {
    private var _binding: FragmentSearchBookBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: SearchBookViewModel by viewModels()
    private lateinit var searchBookAdapter: SearchBookAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        _binding = FragmentSearchBookBinding.bind(view)
        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        searchBookAdapter = SearchBookAdapter(onItemClicked = this::onItemClicked)
        binding.recyclerView.apply {
            adapter = searchBookAdapter
            itemAnimator = null
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val lm = layoutManager as LinearLayoutManager
                    val lastPosition = lm.findLastVisibleItemPosition()
                    if (lastPosition == searchBookAdapter.itemCount - 1)
                        viewModel.loadMore()
                }
            })
        }
    }

    private fun onItemClicked(document: Document) {
        val action =
            SearchBookFragmentDirections.actionSearchBookFragmentToBookDetailFragment(document)
        findNavController().navigate(action)
    }

    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is SearchBookViewModel.SearchUiState.Success -> {
                        binding.progressBar.isVisible = false
                        searchBookAdapter.submitList(uiState.documents)
                    }
                    is SearchBookViewModel.SearchUiState.Error -> {
                        binding.progressBar.isVisible = false
                        when(uiState.errorMessage){
                            NO_DATA->{
                                binding.root.showSnackBar(NO_DATA)
                            }
                        }
                    }
                    is SearchBookViewModel.SearchUiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is SearchBookViewModel.SearchUiState.Search -> {
                        searchBookAdapter.submitList(null)
                        requireContext().hideKeyboard(binding.root)
                        viewModel.fetchBooks(query = uiState.query)
                    }
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        initSearchView(menu)
    }

    private fun initSearchView(menu: Menu) {
        var searchView: SearchView?
        requireActivity().apply {
            val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
            searchView = menu.findItem(R.id.action_search).actionView as SearchView
            searchView?.let {
                it.setSearchableInfo(searchManager.getSearchableInfo(componentName))
                it.maxWidth = Int.MAX_VALUE
                it.isIconified = true
                it.isSubmitButtonEnabled = true
                val searchEdt = it.findViewById(R.id.search_src_text) as EditText
                searchEdt.setOnEditorActionListener { v, actionId, _ ->
                    if (actionId == IME_ACTION_UNSPECIFIED || actionId == IME_ACTION_SEARCH) {
                        val query = v.text.toString()
                        viewModel.setQuery(query = query)
                    }
                    true
                }
                val searchBtn = it.findViewById(R.id.search_go_btn) as View
                searchBtn.setOnClickListener {
                    val query = searchEdt.text.toString()
                    viewModel.setQuery(query = query)
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}