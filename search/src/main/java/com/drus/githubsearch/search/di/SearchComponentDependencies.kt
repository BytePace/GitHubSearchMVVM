package com.drus.githubsearch.search.di

import com.drus.githubsearch.search.domain.GitHubRepository
import com.drus.githubsearch.search.data.GithubRepositoriesApi
import com.drus.githubsearch.search.screens.repositoryDetails.presentation.GithubRepositoryDetailsViewModel
import com.drus.githubsearch.search.screens.search.presentation.SearchGithubRepositoryViewModel

interface SearchComponentDependencies {
    fun getGithubRepositoriesApi(): GithubRepositoriesApi
    fun getGitHubRepository(): GitHubRepository
    fun getSearchGithubRepositoryViewModel(): SearchGithubRepositoryViewModel.Factory
    fun getGithubRepositoryDetailsViewModel(): GithubRepositoryDetailsViewModel.Factory
}