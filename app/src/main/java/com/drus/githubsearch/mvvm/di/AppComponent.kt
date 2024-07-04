package com.drus.githubsearch.mvvm.di

import com.drus.githubsearch.core.di.BaseDependencies
import com.drus.githubsearch.core.utils.AppScope
import com.drus.githubsearch.mvvm.activity.MainViewModel
import com.drus.githubsearch.networking.GitHubRepository
import com.drus.githubsearch.networking.data.GithubRepositoriesApi
import com.drus.githubsearch.networking.di.SearchGithubRepositoryModule
import com.drus.githubsearch.search.di.SearchComponentDependencies
import com.drus.githubsearch.search.screens.repositoryDetails.presentation.GithubRepositoryDetailsViewModel
import com.drus.githubsearch.search.screens.search.presentation.SearchGithubRepositoryViewModel
import com.github.terrakok.cicerone.Router
import dagger.Component
import retrofit2.Retrofit

@AppScope
@Component(
    modules = [
        SearchGithubRepositoryModule::class,
    ], dependencies = [BaseDependencies::class]
)
interface AppComponent : BaseDependencies, SearchComponentDependencies {
    fun mainViewModelFactory(): MainViewModel.Factory
    override fun getGithubRepositoriesApi(): GithubRepositoriesApi
    override fun getGithubRepositoryDetailsViewModel(): GithubRepositoryDetailsViewModel.Factory
    override fun getGitHubRepository(): GitHubRepository
    override fun getSearchGithubRepositoryViewModel(): SearchGithubRepositoryViewModel.Factory
    override val router: Router
    override val retrofit: Retrofit
}