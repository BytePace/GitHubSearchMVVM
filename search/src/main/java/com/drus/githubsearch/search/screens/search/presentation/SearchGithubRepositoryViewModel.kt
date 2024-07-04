package com.drus.githubsearch.search.screens.search.presentation

import android.text.Editable
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.cachedIn
import com.drus.githubsearch.core.presentation.BaseViewModel
import com.drus.githubsearch.search.R
import com.drus.githubsearch.search.Screens
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import com.drus.githubsearch.search.screens.search.domain.GitHubRepository
import com.drus.githubsearch.search.screens.search.validation.SearchValidationUtil
import com.drus.githubsearch.search.utils.TextValidationStatus
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class SearchGithubRepositoryViewModel @AssistedInject constructor(
    private val networkRepository: GitHubRepository,
    private val validationUtil: SearchValidationUtil,
    private val router: Router,
) : BaseViewModel<SearchState, SearchEvent, SearchCommand>() {


    override fun initState(): SearchState {
        return SearchState()
    }

    override fun processEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnRepositoryClick -> navigateToRepositoryDetails(event.repositoryInfo)
        }
    }

    private fun navigateToRepositoryDetails(repositoryInfo: SimpleRepositoryInfo) {
        router.navigateTo(Screens.repositoryDetails(repositoryInfo))
    }

    val isSourceEmpty = MutableLiveData(true)

//    val errorStateText: LiveData<Int?>
//        get() = validationUtil.validationStatusLiveData.map {
//            if (it.searchText.status != TextValidationStatus.CORRECT && it.searchText.showErrorState) {
//                R.string.search_text_too_small_error
//            } else {
//                null
//            }
//        }

    private val searchText = MutableLiveData("")

//    @FlowPreview
//    private val searchState: LiveData<String>
//        get() = searchText.asFlow().debounce(SEARCH_DEBOUNCE).asLiveData(Dispatchers.Default)

//    @FlowPreview
//    private val dataSource = Transformations.switchMap(searchState) {
//        LivePagedListBuilder(
//            RepositoriesDataSourceFactory(it, networkRepository, viewModelScope),
//            pagingConfig
//        ).setBoundaryCallback(boundaryCallback)
//            .build()
//    }

    private val pagingConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(15)
        .build()

    private val boundaryCallback = object : PagedList.BoundaryCallback<SimpleRepositoryInfo>() {
        override fun onZeroItemsLoaded() {
            isSourceEmpty.value = true
        }
    }

    fun onSearchTextChanged(text: Editable?) {
        val result = text.toString()
        Log.d("search", "onSearchTextChanged: $result")
        searchRepositories(result)
        validationUtil.validateSearchText(result, true)
        if (validationUtil.validationStatusLiveData.value?.isAllValid == true) {
            searchText.value = result

        } else {
            searchText.value = ""
        }

    }

    private fun searchRepositories(keyword: String) {
        viewModelScope.launch {
            Log.d("search", "searchRepositories: $keyword")
            networkRepository.search(keyword, 0, 1).flow.cachedIn(viewModelScope)
                .collectLatest { data ->
                    emitNewState {
                        it.copy(repositories = data)
                    }
                }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): SearchGithubRepositoryViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        const val SEARCH_DEBOUNCE = 500L
        fun provideFactory(
            assistedFactory: Factory,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create() as T
            }
        }
    }
}