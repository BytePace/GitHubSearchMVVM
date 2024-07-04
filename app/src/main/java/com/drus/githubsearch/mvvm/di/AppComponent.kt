package com.drus.githubsearch.mvvm.di

import com.drus.githubsearch.core.di.BaseDependencies
import com.drus.githubsearch.core.utils.AppScope
import com.drus.githubsearch.mvvm.activity.MainViewModel
import com.drus.githubsearch.search.domain.GitHubRepository
import com.drus.githubsearch.search.data.GithubRepositoriesApi
import com.drus.githubsearch.search.di.SearchGithubRepositoryModule
import com.drus.githubsearch.search.di.SearchComponentDependencies
import com.drus.githubsearch.search.screens.repositoryDetails.presentation.GithubRepositoryDetailsViewModel
import com.drus.githubsearch.search.screens.search.presentation.SearchGithubRepositoryViewModel
import com.github.terrakok.cicerone.Router
import dagger.Component
import retrofit2.Retrofit

@AppScope
@Component(dependencies = [BaseDependencies::class])
interface AppComponent : BaseDependencies {
    fun mainViewModelFactory(): MainViewModel.Factory
    override val router: Router
    override val retrofit: Retrofit
}