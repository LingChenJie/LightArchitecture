package com.architecture.light.config.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.Call
import java.io.InputStream

/**
 * File describe:OkHttp 加载模型
 * Author: SuQi
 * Create date: 2022/11/14
 * Modify date: 2022/11/14
 * Version: 1
 */
class OkHttpLoader(private val callFactory: Call.Factory) : ModelLoader<GlideUrl, InputStream> {

    override fun handles(model: GlideUrl): Boolean {
        return true
    }

    override fun buildLoadData(
        model: GlideUrl,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream> {
        return ModelLoader.LoadData(model, OkHttpFetcher(callFactory, model))
    }

    class Factory(private val callFactory: Call.Factory) :
        ModelLoaderFactory<GlideUrl, InputStream> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
            return OkHttpLoader(callFactory)
        }

        override fun teardown() {
        }

    }

}