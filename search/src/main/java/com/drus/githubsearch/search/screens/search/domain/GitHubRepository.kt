package com.drus.githubsearch.search.screens.search.domain

import androidx.paging.Pager
import com.drus.githubsearch.search.screens.search.data.models.RepositoryDetails
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo

interface GitHubRepository {
    suspend fun search(
        keyword: String,
        from: Int,
        count: Int
    ): Pager<Int, SimpleRepositoryInfo>

    suspend fun getDetails(info: SimpleRepositoryInfo?): RepositoryDetails?
}