package com.drus.githubsearch.mvvm.activity

import com.drus.githubsearch.core.utils.LoadingContentError
import com.github.terrakok.cicerone.Navigator

data class MainState(
    val screenState: LoadingContentError = LoadingContentError.Content,
)

sealed interface MainEvent {
    data class OnResumeFragments(val navigator: Navigator) : MainEvent
}

sealed interface MainCommand