package com.drus.githubsearch.search.screens.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.drus.githubsearch.core.mvi.BaseViewModel
import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.search.InternalScreens
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import com.drus.githubsearch.search.screens.search.domain.GitHubRepository
import com.drus.githubsearch.search.screens.search.validation.SearchValidationUtil
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val UPLOAD_REPOSITORIES_DEBOUNCE_DELAY = 500L

class SearchRepositoryViewModel @AssistedInject constructor(
    private val networkRepository: GitHubRepository,
    private val validationUtil: SearchValidationUtil,
    private val router: Router,
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
        router.navigateTo(InternalScreens.repositoryDetails(repositoryInfo))
    }

//    val errorStateText: LiveData<Int?>
//        get() = validationUtil.validationStatusLiveData.map {
//            if (it.searchText.status != TextValidationStatus.CORRECT && it.searchText.showErrorState) {
//                R.string.search_text_too_small_error
//            } else {
//                null
//            }
//        }


    private fun onSearchTextChanged(text: String) {
        validationUtil.validateSearchText(text, true)
        if (validationUtil.validationStatusLiveData.value?.isAllValid == true) {
            searchRepositories(text)
        }

    }

    private fun searchRepositories(keyword: String) {
        viewModelScope.launch {
            debounceJob?.join()
            debounceJob = viewModelScope.launch(coroutineContext) {
                delay(UPLOAD_REPOSITORIES_DEBOUNCE_DELAY)
                val flow = networkRepository.search(keyword, 0, 1).flow.cachedIn(viewModelScope)
                emitNewState {
                    it.copy(
                        screenState = LoadingContentError.Content,
                        repositories = flow
                    )
                }
                debounceJob = null
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): SearchRepositoryViewModel
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