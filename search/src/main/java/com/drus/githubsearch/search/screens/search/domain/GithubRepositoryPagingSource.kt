package com.drus.githubsearch.search.screens.search.domain

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.drus.githubsearch.search.screens.search.data.GithubRepositoriesApi
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo


class GithubRepositoryPagingSource(
    private val api: GithubRepositoriesApi,
    private val query: String,
) : PagingSource<Int, SimpleRepositoryInfo>() {

    override fun getRefreshKey(state: PagingState<Int, SimpleRepositoryInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
//        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimpleRepositoryInfo> {
        return try {
            // Start refresh at page 1 if undefined.
            Log.d("search", "load: nextPageNumber ")
            val nextPageNumber = params.key ?: 1
            Log.d("search", "load: nextPageNumber $nextPageNumber")
            val response =
                api.searchRepositories(keyWord = query, pageNum = nextPageNumber, sizePage = 15)
            val result = response.await()
            Log.d("search", "load: response $response")
            val nextPage = if (result.body()?.list?.size == result.body()?.totalCount) {
                null
            } else {
                nextPageNumber + 1
            }
            LoadResult.Page(
                data = result.body()?.list ?: emptyList(),
                prevKey = null, // Only paging forward.
                nextKey = nextPage,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}