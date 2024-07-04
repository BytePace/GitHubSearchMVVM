package com.drus.githubsearch.networking.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.drus.githubsearch.networking.GitHubRepository
import com.drus.githubsearch.networking.GithubRepositoryPagingSource
import com.drus.githubsearch.networking.data.models.RepositoryDetails
import com.drus.githubsearch.networking.data.models.SimpleRepositoryInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val githubRepositoriesApi: GithubRepositoriesApi
) : GitHubRepository {

    override suspend fun getDetails(info: SimpleRepositoryInfo?): RepositoryDetails? {
        info ?: throw NullPointerException()
        val response = githubRepositoriesApi.getRepositoryDetails(
            info.repositoryOwner.userName,
            info.repositoryName
        ).await()
        if (!response.isSuccessful)
            throw Exception(response.errorBody()?.string())
        return response.body()
    }

    override suspend fun search(
        keyword: String,
        from: Int,
        count: Int
    ): Pager<Int, SimpleRepositoryInfo> = withContext(Dispatchers.IO) {
        Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = {
                GithubRepositoryPagingSource(
                    api = githubRepositoriesApi,
                    query = keyword,
                )
            }
        )
    }
}