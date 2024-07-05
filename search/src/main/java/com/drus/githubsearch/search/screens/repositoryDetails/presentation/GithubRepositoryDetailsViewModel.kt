package com.drus.githubsearch.search.screens.repositoryDetails.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.drus.githubsearch.core.presentation.BaseViewModel
import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.search.data.models.SimpleRepositoryInfoDto
import com.drus.githubsearch.search.domain.GitHubRepository
import com.drus.githubsearch.search.domain.models.SimpleRepositoryInfo
import com.github.terrakok.cicerone.Router
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GithubRepositoryDetailsViewModel @AssistedInject constructor(
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
            is GithubRepositoryDetailsEvent.OnBackButtonClick -> navigateBack()
        }
    }

    private fun startInit(info: SimpleRepositoryInfo) {
        viewModelScope.launch {
            try {
                val details = githubRepository.getDetails(info)
                emitNewState {
                    it.copy(
                        screenState = LoadingContentError.Content,
                        lastCommitDate = details?.commitDate ?: "",
                    )
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                //TODO добавить обработку ошибок
                Log.d("error", "errorMessage: ${t.localizedMessage}")
            }

        }
    }

    private fun navigateBack() {
        router.exit()
    }

    @AssistedFactory
    interface Factory {
        fun create(info: SimpleRepositoryInfo?): GithubRepositoryDetailsViewModel
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