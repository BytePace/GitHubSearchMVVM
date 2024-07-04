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
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.search.databinding.FragmentSearchRepositoriesBinding
import com.drus.githubsearch.search.di.SearchGithubRepositoryComponentProvider
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import com.drus.githubsearch.search.screens.search.presentation.adapter.RepositoriesAdapter
import kotlinx.coroutines.launch

class SearchGithubRepositoryFragment : Fragment() {


    private val viewModel by viewModels<SearchGithubRepositoryViewModel> {
        SearchGithubRepositoryViewModel.provideFactory(
            (parentFragment as SearchGithubRepositoryComponentProvider).getSearchGithubRepositoryComponent()
                .searchGithubRepositoryViewModelFactory()
        )
    }
    private var _binding: FragmentSearchRepositoriesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val repositoriesAdapter = RepositoriesAdapter {
        viewModel.processEvent(SearchEvent.OnRepositoryClick(it))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        binding.searchInputLayout.apply {
            editText.addTextChangedListener {
                viewModel.onSearchTextChanged(it)
            }
//            setErrorText(viewModel.errorStateText.value)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager?.onRestoreInstanceState(
                savedInstanceState?.getParcelable(RV_STATE)
            )
            adapter = repositoriesAdapter
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    when (state.screenState) {
                        LoadingContentError.Content -> uploadScreenInfo(state.repositories)
                        LoadingContentError.Error -> Unit
                        LoadingContentError.Init -> Unit
                        LoadingContentError.Loading -> Unit
                    }
                }
            }
        }
    }

    private suspend fun uploadScreenInfo(repositories: PagingData<SimpleRepositoryInfo>) {
        repositoriesAdapter.submitData(repositories)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(RV_STATE, binding.recyclerView.layoutManager?.onSaveInstanceState())
    }

    private companion object {
        private const val TAG = "SearchFragment"
        const val RV_STATE = "$TAG:rvState"
    }
}