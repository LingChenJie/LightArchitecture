package com.android.architecture.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/31
 * Modify date: 2022/8/31
 * Version: 1
 */
class ServiceCreator {

    companion object {

        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ServiceCreator()
        }
    }

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()


    fun <T> create(baseUrl: String, serviceClass: Class<T>): T =
        getRetrofit(baseUrl).create(serviceClass)

    inline fun <reified T> create(baseUrl: String): T = create(baseUrl, T::class.java)

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}