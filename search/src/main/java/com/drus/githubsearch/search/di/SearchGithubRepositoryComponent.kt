package com.drus.githubsearch.search.di

import com.drus.githubsearch.core.di.BaseDependencies
import com.drus.githubsearch.search.screens.repositoryDetails.presentation.GithubRepositoryDetailsViewModel
import com.drus.githubsearch.search.screens.search.presentation.SearchGithubRepositoryViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [SearchGithubRepositoryModule::class],
    dependencies = [BaseDependencies::class]
)
interface SearchGithubRepositoryComponent {
    fun searchGithubRepositoryViewModelFactory(): SearchGithubRepositoryViewModel.Factory
    fun githubRepositoryDetailsViewModelFactory(): GithubRepositoryDetailsViewModel.Factory
}