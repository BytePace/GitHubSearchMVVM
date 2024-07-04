package com.drus.githubsearch.search.screens.repositoryDetails.presentation

import com.drus.githubsearch.core.utils.LoadingContentError
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo

data class GithubRepositoryDetailsState(
    val screenState: LoadingContentError = LoadingContentError.Init,
    val repositoryName: String = "",
    val lastCommitDate: String = "",
)

sealed interface GithubRepositoryDetailsEvent {
    data object OnBackButtonClick : GithubRepositoryDetailsEvent
}

sealed interface GithubRepositoryDetailsCommand {

}