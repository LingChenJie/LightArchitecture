package com.architecture.light.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.architecture.light.data.remote.GithubService
import com.architecture.light.data.remote.bean.Repo
import com.architecture.light.data.remote.paging.RepoPagingSource
import kotlinx.coroutines.flow.Flow

/**
 * Created by SuQi on 2022/10/23.
 * Describe:
 */
object RepoRepository {
    private const val PAGE_SIZE = 50
    private val githubService = GithubService.create()

    fun getPagingData(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { RepoPagingSource(githubService) }
        ).flow
    }
}