package com.drus.githubsearch.search

import com.drus.githubsearch.search.presentation.FlowFragment
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import com.drus.githubsearch.search.screens.repositoryDetails.presentation.GithubRepositoryDetailsFragment
import com.drus.githubsearch.search.screens.search.presentation.SearchGithubRepositoryFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

internal object InternalScreens {

    fun searchFlow() = FragmentScreen { FlowFragment() }
    fun search() = FragmentScreen { SearchGithubRepositoryFragment() }
    fun repositoryDetails(info: SimpleRepositoryInfo) = FragmentScreen {
        GithubRepositoryDetailsFragment.newInstance(info)
    }
}