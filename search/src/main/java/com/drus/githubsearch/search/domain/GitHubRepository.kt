package com.drus.githubsearch.search.domain

import androidx.paging.Pager
import com.drus.githubsearch.search.domain.models.RepositoryDetails
import com.drus.githubsearch.search.domain.models.SimpleRepositoryInfo

interface GitHubRepository {
    suspend fun search(
        keyword: String,
        from: Int,
        count: Int
    ): Pager<Int, SimpleRepositoryInfo>

    suspend fun getDetails(info: SimpleRepositoryInfo?): RepositoryDetails?
}