package com.drus.githubsearch.search.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.drus.githubsearch.core.utils.AppDispatchers
import com.drus.githubsearch.search.domain.GitHubRepository
import com.drus.githubsearch.search.domain.models.SimpleRepositoryInfo
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val githubRepositoriesApi: GithubRepositoriesApi,
    private val dispatchers: AppDispatchers,
) : GitHubRepository {

    override suspend fun getDetails(info: SimpleRepositoryInfo?) = withContext(dispatchers.io) {
        info ?: throw NullPointerException()
        val response = githubRepositoriesApi.getRepositoryDetails(
            owner = info.repositoryOwner.userName,
            repo = info.repositoryName,
        ).await()
        if (!response.isSuccessful)
            throw Exception(response.errorBody()?.string())
        response.body()?.toDomain()
    }

    override suspend fun search(
        keyword: String,
        from: Int,
        count: Int
    ): Pager<Int, SimpleRepositoryInfo> = withContext(dispatchers.io) {
        Pager(
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