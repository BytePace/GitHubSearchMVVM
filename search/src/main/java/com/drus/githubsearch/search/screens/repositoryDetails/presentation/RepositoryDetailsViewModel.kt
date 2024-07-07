package com.drus.githubsearch.search.screens.repositoryDetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.drus.githubsearch.core.mvi.BaseViewModel
import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import com.drus.githubsearch.search.screens.search.domain.GitHubRepository
import com.github.terrakok.cicerone.Router
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel @AssistedInject constructor(
    private val githubRepository: GitHubRepository,
    private val router: Router,
    @Assisted private val repositoryInfo: SimpleRepositoryInfo?,
) : BaseViewModel<GithubRepositoryDetailsState, GithubRepositoryDetailsEvent, GithubRepositoryDetailsCommand>() {

    override fun initState(): GithubRepositoryDetailsState {
        return GithubRepositoryDetailsState()
    }

    init {
        repositoryInfo?.let {
            emitNewState { state ->
                state.copy(repositoryName = it.repositoryName)
            }
            startInit(it)
        }
    }

    override fun processEvent(event: GithubRepositoryDetailsEvent) {
        when (event) {
            is GithubRepositoryDetailsEvent.OnBackButtonClick -> {
                navigateBack()
            }
        }
    }

    private fun startInit(info: SimpleRepositoryInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            val details = githubRepository.getDetails(info)
            emitNewState {
                it.copy(
                    screenState = LoadingContentError.Content,
                    lastCommitDate = details?.commit?.details?.author?.date ?: "",
                )
            }
        }
    }

    private fun navigateBack() {
        router.exit()
    }

    @AssistedFactory
    interface Factory {
        fun create(info: SimpleRepositoryInfo?): RepositoryDetailsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            info: SimpleRepositoryInfo?,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(info) as T
            }
        }
    }
}