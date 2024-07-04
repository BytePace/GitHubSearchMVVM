package com.drus.githubsearch.search.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.drus.githubsearch.search.domain.models.SimpleRepositoryInfo


class GithubRepositoryPagingSource(
    private val api: GithubRepositoriesApi,
    private val query: String,
) : PagingSource<Int, SimpleRepositoryInfo>() {

    override fun getRefreshKey(state: PagingState<Int, SimpleRepositoryInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimpleRepositoryInfo> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response =
                api.searchRepositories(keyWord = query, pageNum = nextPageNumber, sizePage = 15)
            val result = response.await()
            val nextPage = if (result.body()?.list?.size == result.body()?.totalCount) {
                null
            } else {
                nextPageNumber + 1
            }
            LoadResult.Page(
                data = result.body()?.list?.map { it.toDomain() } ?: emptyList(),
                prevKey = null,
                nextKey = nextPage,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}