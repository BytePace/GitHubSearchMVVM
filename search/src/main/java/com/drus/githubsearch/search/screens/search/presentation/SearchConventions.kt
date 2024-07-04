package com.drus.githubsearch.search.screens.search.presentation

import androidx.paging.PagingData
import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchState(
    val screenState: LoadingContentError = LoadingContentError.Init,
    val repositories: Flow<PagingData<SimpleRepositoryInfo>> = emptyFlow(),
)

sealed interface SearchEvent {
    data class OnRepositoryClick(val repositoryInfo: SimpleRepositoryInfo) : SearchEvent
    data class OnSearchTextChanged(val searchText: String) : SearchEvent
}

sealed interface SearchCommand {

}