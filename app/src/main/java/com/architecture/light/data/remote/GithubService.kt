package com.architecture.light.data.remote

import com.android.architecture.data.remote.RetrofitCreator
import com.architecture.light.data.remote.bean.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by SuQi on 2022/10/23.
 * Describe:
 */
interface GithubService {

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubService = RetrofitCreator.create(BASE_URL)
    }

    @GET("search/repositories?sort=stars&q=Android")
    suspend fun searchRepos(@Query("page") page: Int, @Query("per_page") perPage: Int): RepoResponse
}