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
import com.drus.githubsearch.search.R
import com.drus.githubsearch.search.databinding.FragmentSearchRepositoriesBinding
import com.drus.githubsearch.search.di.FlowComponentProvider
import com.drus.githubsearch.search.screens.repositoryDetails.presentation.GithubRepositoryDetailsFragment
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import com.drus.githubsearch.search.screens.search.presentation.adapter.RepositoriesAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchGithubRepositoryFragment : Fragment() {


    private val viewModel by viewModels<SearchRepositoryViewModel> {
        SearchRepositoryViewModel.provideFactory(
            (parentFragment as FlowComponentProvider).getSearchGithubRepositoryComponent()
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
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        val fragment = GithubRepositoryDetailsFragment.newInstance(it)
        fragmentTransaction
            .replace(R.id.container, fragment, tag)
            .addToBackStack(fragment.javaClass.canonicalName)
            .setReorderingAllowed(true)
            .commit()
        parentFragmentManager.executePendingTransactions()
//        viewModel.processEvent(SearchEvent.OnRepositoryClick(it))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        bindSearchInput()
        bindRepositoryRecyclerView(savedInstanceState)
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

    private suspend fun uploadScreenInfo(repositories: Flow<PagingData<SimpleRepositoryInfo>>) {
        lifecycleScope.launch {
            repositories.collectLatest {
                repositoriesAdapter.submitData(it)
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

    private fun bindRepositoryRecyclerView(savedInstanceState: Bundle?) {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager?.onRestoreInstanceState(
                savedInstanceState?.getParcelable(RV_STATE)
            )
            adapter = repositoriesAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        private const val TAG = "SearchFragment"
        const val RV_STATE = "$TAG:rvState"
    }
}