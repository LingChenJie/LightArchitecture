package com.architecture.light.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.architecture.light.data.remote.GithubService
import com.architecture.light.data.remote.bean.Repo

/**
 * Created by SuQi on 2022/10/23.
 * Describe:
 */
class RepoPagingSource(private val githubService: GithubService) : PagingSource<Int, Repo>() {

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val repoResponse = githubService.searchRepos(page, pageSize)
            val repoItems = repoResponse.items
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

}