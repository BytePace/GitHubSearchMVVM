package com.drus.githubsearch.search

import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import com.drus.githubsearch.search.screens.repositoryDetails.RepositoryDetailsFragment
import com.drus.githubsearch.search.screens.search.presentation.SearchGithubRepositoryFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun search() = FragmentScreen { SearchGithubRepositoryFragment() }
    fun repositoryDetails(info: SimpleRepositoryInfo) = FragmentScreen {
        RepositoryDetailsFragment.newInstance(info)
    }
}