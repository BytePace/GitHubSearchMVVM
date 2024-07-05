package com.drus.githubsearch.search.screens.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.search.databinding.FragmentSearchRepositoriesBinding
import com.drus.githubsearch.search.di.SearchGithubRepositoryComponentProvider
import com.drus.githubsearch.search.screens.search.presentation.adapter.GithubRepositoriesAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "SearchFragment"

class SearchGithubRepositoryFragment : Fragment() {

    private val viewModel by viewModels<SearchGithubRepositoryViewModel> {
        SearchGithubRepositoryViewModel.provideFactory(
            assistedFactory = (activity?.application as SearchGithubRepositoryComponentProvider)
                .getSearchGithubRepositoryComponent()
                .searchGithubRepositoryViewModelFactory(),
        )
    }
    private var _binding: FragmentSearchRepositoriesBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val githubRepositoriesAdapter = GithubRepositoriesAdapter {
        viewModel.processEvent(SearchEvent.OnRepositoryClick(it))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        bindSearchInput()
        bindRepositoryRecyclerView()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    when (state.screenState) {
                        LoadingContentError.Content -> uploadScreenInfo(state)
                        LoadingContentError.Error -> Unit
                        LoadingContentError.Init -> Unit
                        LoadingContentError.Loading -> Unit
                    }
                }
            }
        }
    }

    private suspend fun uploadScreenInfo(state: SearchState) {
        if (state.error.isBlank()) {
            binding.searchInputLayout.hideError()
        } else {
            binding.searchInputLayout.showError(state.error)
        }
        lifecycleScope.launch {
            state.repositories.collectLatest {
                githubRepositoriesAdapter.submitData(it)
            }
        }
    }

    private fun bindSearchInput() {
        binding.searchInputLayout.apply {
            editText.addTextChangedListener {
                viewModel.processEvent(SearchEvent.OnSearchTextChanged(it.toString()))
            }
        }
    }

    private fun bindRepositoryRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = githubRepositoriesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}