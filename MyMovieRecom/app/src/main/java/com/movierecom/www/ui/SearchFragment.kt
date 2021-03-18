package com.movierecom.www.ui

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.EditorInfo.IME_ACTION_UNSPECIFIED
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movierecom.www.R
import com.movierecom.www.databinding.FragmentSearchBinding
import com.movierecom.www.model.NaverMovie
import com.movierecom.www.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var searchAdapter: SearchAdapter
    private val args by navArgs<SearchFragmentArgs>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        setHasOptionsMenu(true)
        args.query?.let{
            Log.e("fuck", "Come here ? " + it)
            viewModel.searchQuery(it)
        }
        initRecyclerView()
        subscribeObservers()
    }


    private fun initRecyclerView() {
        searchAdapter = SearchAdapter(::onMovieItemClick)
        binding.recyclerview.apply {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), VERTICAL)
            addItemDecoration(dividerItemDecoration)
            adapter = searchAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val lm = layoutManager as LinearLayoutManager
                    val lastPosition = lm.findLastVisibleItemPosition()
                    if (lastPosition == searchAdapter.itemCount - 1) {
                        onLoadMore()
                    }
                }
            })
        }
    }

    private fun onMovieItemClick(naverMovie: NaverMovie) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(naverMovie = naverMovie)
        findNavController().navigate(action)
    }

    private fun subscribeObservers() {
        viewModel.movieSet.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            searchAdapter.submitList(it.toList())
        }
    }

    private fun onLoadMore() {
        binding.progressBar.isVisible = true
        viewModel.nextQuery()
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

            args.query?.let{
                searchEdt.setText(it)
                searchEdt.clearFocus()
            }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}