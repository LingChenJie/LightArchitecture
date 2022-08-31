package com.android.architecture.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/31
 * Modify date: 2022/8/31
 * Version: 1
 */
object RetrofitCreator {

    inline fun <reified T> create(baseUrl: String, okHttpClient: OkHttpClient? = null): T =
        create(baseUrl, T::class.java, okHttpClient)

    fun <T> create(baseUrl: String, serviceClass: Class<T>, okHttpClient: OkHttpClient? = null): T =
        getRetrofit(baseUrl, okHttpClient).create(serviceClass)

    fun getRetrofit(baseUrl: String, okHttpClient: OkHttpClient? = null): Retrofit {
        val client = okHttpClient ?: HttpRequest().okHttpClient
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}