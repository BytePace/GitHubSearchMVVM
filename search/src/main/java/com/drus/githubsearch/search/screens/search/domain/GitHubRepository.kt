package com.drus.githubsearch.search.screens.search.domain

import com.drus.githubsearch.search.screens.search.data.models.RepositoryDetails
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo

interface GitHubRepository {
    suspend fun search(keyword: String?, from: Int, count: Int): List<SimpleRepositoryInfo>
    suspend fun getDetails(info: SimpleRepositoryInfo?): RepositoryDetails?
}