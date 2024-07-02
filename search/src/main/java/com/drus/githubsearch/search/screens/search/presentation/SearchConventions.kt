package com.drus.githubsearch.search.screens.search.presentation

import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo

data class SearchState(
    val screenState: LoadingContentError = LoadingContentError.Init,
)

sealed interface SearchEvent {
    data class OnRepositoryClick(val repositoryInfo: SimpleRepositoryInfo) : SearchEvent
}

sealed interface SearchCommand {

}