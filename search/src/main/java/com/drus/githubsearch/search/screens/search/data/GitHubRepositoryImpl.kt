package com.drus.githubsearch.search.screens.search.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.drus.githubsearch.search.screens.search.data.models.RepositoryDetails
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import com.drus.githubsearch.search.screens.search.domain.GithubRepositoryPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val githubRepositoriesApi: GithubRepositoriesApi
) : com.drus.githubsearch.search.screens.search.domain.GitHubRepository {

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
//        if (!response.isSuccessful)
//            throw Exception(response.errorBody()?.string())
//        return response.body()?.list ?: listOf()
    }

    private fun getPageNumberByPosition(from: Int, count: Int): Int {
        if (from == 0) return 1
        if (count == 0) throw IllegalArgumentException("page size must not be null")
        if (from < count) return 1
        return from / count + 1
    }
}