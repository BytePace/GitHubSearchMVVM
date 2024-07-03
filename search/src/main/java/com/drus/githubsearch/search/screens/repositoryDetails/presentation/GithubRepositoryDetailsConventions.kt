package com.drus.githubsearch.search.screens.repositoryDetails.presentation

import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo

data class GithubRepositoryDetailsState(
    val screenState: LoadingContentError = LoadingContentError.Init,
)

sealed interface GithubRepositoryDetailsEvent {
    data class OnRepositoryClick(val repositoryInfo: SimpleRepositoryInfo) : GithubRepositoryDetailsEvent
}

sealed interface GithubRepositoryDetailsCommand {

}