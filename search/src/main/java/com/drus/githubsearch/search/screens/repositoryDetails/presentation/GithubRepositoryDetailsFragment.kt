package com.drus.githubsearch.search.screens.repositoryDetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.core.utils.SaveClickListener
import com.drus.githubsearch.search.R
import com.drus.githubsearch.search.databinding.FragmentRepositoryDetailsBinding
import com.drus.githubsearch.search.di.SearchGithubRepositoryComponentProvider
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import kotlinx.coroutines.launch

class GithubRepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {

    private val viewModel by viewModels<GithubRepositoryDetailsViewModel> {
        GithubRepositoryDetailsViewModel.provideFactory(
            assistedFactory = (parentFragment as SearchGithubRepositoryComponentProvider)
                .getSearchGithubRepositoryComponent()
                .githubRepositoryDetailsViewModelFactory(),
            info = requireArguments().getParcelable(INFO),
        )
    }
    private var _binding: FragmentRepositoryDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        bindBackButton()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    when (state.screenState) {
                        LoadingContentError.Content -> updateScreenInfo(state.lastCommitDate)
                        LoadingContentError.Error -> Unit
                        LoadingContentError.Init -> updateRepositoryName(state.repositoryName)
                        LoadingContentError.Loading -> Unit
                    }
                }
            }
        }
    }

    private fun updateScreenInfo(date: String) {
        with(binding.lastCommitDate) {
            text = date
        }
    }

    private fun updateRepositoryName(repositoryName: String) {
        binding.repositoryName.text = repositoryName
    }

    private fun bindBackButton() {
        binding.backArrow.setOnClickListener {
            viewModel.processEvent(GithubRepositoryDetailsEvent.OnBackButtonClick)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "RepositoryDetailsFragment"
        const val INFO = "$TAG:info"

        fun newInstance(
            info: SimpleRepositoryInfo
        ) = GithubRepositoryDetailsFragment().apply {
            arguments = bundleOf(
                INFO to info
            )
        }
    }
}