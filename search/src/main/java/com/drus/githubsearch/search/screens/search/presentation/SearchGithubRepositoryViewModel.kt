package com.drus.githubsearch.search.screens.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.drus.githubsearch.core.presentation.BaseViewModel
import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.core.utils.StringProvider
import com.drus.githubsearch.search.R
import com.drus.githubsearch.search.Screens
import com.drus.githubsearch.search.domain.GitHubRepository
import com.drus.githubsearch.search.domain.models.SimpleRepositoryInfo
import com.drus.githubsearch.search.screens.search.validation.SearchValidationUtil
import com.drus.githubsearch.search.utils.TextValidationStatus
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val UPLOAD_REPOSITORIES_DEBOUNCE_DELAY = 500L

class SearchGithubRepositoryViewModel @AssistedInject constructor(
    private val networkRepository: GitHubRepository,
    private val validationUtil: SearchValidationUtil,
    private val router: Router,
    private val stringProvider: StringProvider,
) : BaseViewModel<SearchState, SearchEvent, SearchCommand>() {

    private var debounceJob: Job? = null
    override fun initState(): SearchState {
        return SearchState()
    }

    override fun processEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnRepositoryClick -> navigateToRepositoryDetails(event.repositoryInfo)
            is SearchEvent.OnSearchTextChanged -> onSearchTextChanged(event.searchText)
        }
    }

    private fun navigateToRepositoryDetails(repositoryInfo: SimpleRepositoryInfo) {
        router.navigateTo(Screens.repositoryDetails(repositoryInfo))
    }


    private fun onSearchTextChanged(text: String) {
        validationUtil.validateSearchText(text, true)
        val validationValue = validationUtil.validationStatusLiveData.value
        if (validationValue?.isAllValid == true) {
            emitNewState {
                it.copy(error = "")
            }
            searchRepositories(text)
        } else {
            if (validationValue?.searchText?.status != TextValidationStatus.CORRECT &&
                validationValue?.searchText?.showErrorState == true
            ) {
                emitNewState {
                    it.copy(error = stringProvider.getString(R.string.search_text_too_small_error))
                }
            }
        }
    }

    private fun searchRepositories(keyword: String) {
        viewModelScope.launch {
            try {
                debounceJob?.cancel()
                debounceJob = viewModelScope.launch(coroutineContext) {
                    delay(UPLOAD_REPOSITORIES_DEBOUNCE_DELAY)
                    val flowOfRepositories =
                        networkRepository.search(keyword, 0, 1).flow.cachedIn(viewModelScope)
                    emitNewState {
                        it.copy(
                            screenState = LoadingContentError.Content,
                            repositories = flowOfRepositories,
                        )
                    }
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                //TODO добавить обработку ошибок
                Log.d("error", "errorMessage: ${t.localizedMessage}")
            }

        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): SearchGithubRepositoryViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create() as T
            }
        }
    }
}