package com.drus.githubsearch.search.di

import com.drus.githubsearch.core.utils.AppScope
import com.drus.githubsearch.search.data.GitHubRepositoryImpl
import com.drus.githubsearch.search.data.GithubRepositoriesApi
import com.drus.githubsearch.search.domain.GitHubRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class SearchGithubRepositoryModule {
    @Provides
    @AppScope
    fun provideGithubRepositoriesApi(retrofit: Retrofit): GithubRepositoriesApi {
        return retrofit.create(GithubRepositoriesApi::class.java)
    }

    @Provides
    @AppScope
    fun provideRepository(service: GithubRepositoriesApi): GitHubRepository {
        return GitHubRepositoryImpl(service)
    }
}