package com.drus.githubsearch.networking.di

import com.drus.githubsearch.core.utils.AppScope
import com.drus.githubsearch.core.utils.FeatureScope
import com.drus.githubsearch.networking.GitHubRepository
import com.drus.githubsearch.networking.data.GitHubRepositoryImpl
import com.drus.githubsearch.networking.data.GithubRepositoriesApi
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