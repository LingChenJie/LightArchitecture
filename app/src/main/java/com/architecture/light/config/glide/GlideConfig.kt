package com.architecture.light.config.glide

import android.content.Context
import com.android.architecture.data.remote.HttpRequest
import com.architecture.light.R
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.InputStream

/**
 * File describe:Glide 全局配置
 * Author: SuQi
 * Create date: 2022/11/14
 * Modify date: 2022/11/14
 * Version: 1
 */
@GlideModule
class GlideConfig : AppGlideModule() {

    companion object {
        private const val IMAGE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024L
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val diskCacheFile = File(context.cacheDir, "glide")
        if (diskCacheFile.exists() && diskCacheFile.isFile) {
            diskCacheFile.delete()
        }
        if (!diskCacheFile.exists()) {
            diskCacheFile.mkdirs()
        }
        builder.setDiskCache {
            DiskLruCacheWrapper.create(
                diskCacheFile,
                IMAGE_DISK_CACHE_MAX_SIZE
            )
        }
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize
        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toLong()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toLong()

        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize))
        builder.setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_image_loading)// 设置默认加载中占位图
                .error(R.drawable.ic_image_error)// 设置默认加载出错占位图
        )
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // Glide 默认使用的是 HttpURLConnection 来做网络请求，这里切换成更高效的 OkHttp
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpLoader.Factory(HttpRequest().okHttpClient)
        )
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}