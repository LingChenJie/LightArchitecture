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
class RetrofitCreator {

    companion object {

        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitCreator()
        }
    }

    inline fun <reified T> create(baseUrl: String, okHttpClient: OkHttpClient? = null): T {
        return if (okHttpClient != null) {
            create(baseUrl, T::class.java, okHttpClient)
        } else {
            create(baseUrl, T::class.java, HttpRequest().okHttpClient)
        }
    }

    fun <T> create(baseUrl: String, serviceClass: Class<T>, okHttpClient: OkHttpClient): T =
        getRetrofit(baseUrl, okHttpClient).create(serviceClass)

    private fun getRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}