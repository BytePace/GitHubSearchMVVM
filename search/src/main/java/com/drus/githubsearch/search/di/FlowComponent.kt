package com.drus.githubsearch.search.di

import com.drus.githubsearch.core.di.BaseDependencies
import com.drus.githubsearch.core.utils.FeatureScope
import com.drus.githubsearch.search.screens.repositoryDetails.presentation.RepositoryDetailsViewModel
import com.drus.githubsearch.search.screens.search.presentation.SearchRepositoryViewModel
import dagger.Component

@FeatureScope
@Component(
    modules = [SearchGithubRepositoryModule::class],
    dependencies = [BaseDependencies::class]
)
interface FlowComponent {
    fun searchGithubRepositoryViewModelFactory(): SearchRepositoryViewModel.Factory
    fun githubRepositoryDetailsViewModelFactory(): RepositoryDetailsViewModel.Factory
}