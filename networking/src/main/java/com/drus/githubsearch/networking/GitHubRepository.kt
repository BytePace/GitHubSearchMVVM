package com.drus.githubsearch.networking

import androidx.paging.Pager
import com.drus.githubsearch.networking.data.models.RepositoryDetails
import com.drus.githubsearch.networking.data.models.SimpleRepositoryInfo

interface GitHubRepository {
    suspend fun search(
        keyword: String,
        from: Int,
        count: Int
    ): Pager<Int, SimpleRepositoryInfo>

    suspend fun getDetails(info: SimpleRepositoryInfo?): RepositoryDetails?
}