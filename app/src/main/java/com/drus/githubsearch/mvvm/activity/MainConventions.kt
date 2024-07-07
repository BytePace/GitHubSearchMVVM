package com.drus.githubsearch.mvvm.activity

data class MainState(
    val text: String = "",
)

sealed interface MainEvent

sealed interface MainEffect