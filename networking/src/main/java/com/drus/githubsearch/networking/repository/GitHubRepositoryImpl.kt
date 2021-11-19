package com.drus.githubsearch.networking.repository

import com.drus.githubsearch.networking.NetworkService
import com.drus.githubsearch.networking.models.RepositoryDetails
import com.drus.githubsearch.networking.models.SimpleRepositoryInfo
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : GitHubRepository {

    override suspend fun getDetails(info: SimpleRepositoryInfo): RepositoryDetails {
        return networkService.getRepositoryDetails(
            info.repositoryOwner.userName,
            info.repositoryName
        )
    }

    override suspend fun search(
        keyword: String?,
        from: Int,
        count: Int
    ): List<SimpleRepositoryInfo> {
        return if (keyword.isNullOrBlank()) {
            emptyList()
        } else {
            networkService.searchRepositories(
                keyword,
                getPageNumberByPosition(from, count),
                count
            ).list
        }
    }

    private fun getPageNumberByPosition(from: Int, count: Int): Int {
        if (from == 0) return 1
        if (count == 0) throw IllegalArgumentException("page size must not be null")
        if (from < count) return 1
        return from / count + 1
    }
}