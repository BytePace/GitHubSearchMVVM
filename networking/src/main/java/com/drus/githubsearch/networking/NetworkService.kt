package com.drus.githubsearch.networking

import com.drus.githubsearch.networking.models.RepositoryDetails
import com.drus.githubsearch.networking.models.RepositoryInfoList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") keyWord: String,
        @Query("page") pageNum: Int,
        @Query("per_page") sizePage: Int
    ): RepositoryInfoList

    @GET("repos/{owner}/{repo}/branches/master")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepositoryDetails
}