package com.drus.githubsearch.core.utils

sealed interface LoadingContentError {
    data object Init: LoadingContentError
    data object Loading : LoadingContentError
    data object Content : LoadingContentError
    data object Error : LoadingContentError
}