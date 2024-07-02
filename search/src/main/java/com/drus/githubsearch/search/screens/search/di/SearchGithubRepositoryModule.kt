package com.drus.githubsearch.search.screens.search.di

import com.drus.githubsearch.search.screens.search.data.GitHubRepositoryImpl
import com.drus.githubsearch.search.screens.search.data.GithubRepositoriesApi
import com.drus.githubsearch.search.screens.search.domain.GitHubRepository
import dagger.Provides
import retrofit2.Retrofit

class SearchGithubRepositoryModule {

    @Provides
    fun provideGithubRepositoriesApi(retrofit: Retrofit): GithubRepositoriesApi {
        return retrofit.create(GithubRepositoriesApi::class.java)
    }

    @Provides
    fun provideRepository(service: GithubRepositoriesApi): GitHubRepository {
        return GitHubRepositoryImpl(service)
    }
}