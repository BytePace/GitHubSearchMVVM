package com.drus.githubsearch.search.screens.search.data

import com.drus.githubsearch.search.screens.search.data.models.RepositoryDetails
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import java.lang.NullPointerException
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val githubRepositoriesApi: GithubRepositoriesApi
): com.drus.githubsearch.search.screens.search.domain.GitHubRepository {

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
        keyword: String?,
        from: Int,
        count: Int
    ): List<SimpleRepositoryInfo> {
        if (keyword.isNullOrBlank()) return emptyList()
        val response = githubRepositoriesApi.searchRepositories(
            keyword,
            getPageNumberByPosition(from, count),
            count
        ).await()
        if (!response.isSuccessful)
            throw Exception(response.errorBody()?.string())
        return response.body()?.list ?: listOf()
    }

    private fun getPageNumberByPosition(from: Int, count: Int): Int {
        if (from == 0) return 1
        if (count == 0) throw IllegalArgumentException("page size must not be null")
        if (from < count) return 1
        return from / count + 1
    }
}