package com.architecture.light.config.glide

import com.android.architecture.utils.CloseableUtils
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import com.bumptech.glide.util.Preconditions
import okhttp3.*
import java.io.IOException
import java.io.InputStream

/**
 * File describe:OkHttp 加载器
 * Author: SuQi
 * Create date: 2022/11/14
 * Modify date: 2022/11/14
 * Version: 1
 */
class OkHttpFetcher(
    private val callFactory: Call.Factory,
    private val glideUrl: GlideUrl
) : DataFetcher<InputStream>, Callback {

    private var mInputStream: InputStream? = null
    private var mResponseBody: ResponseBody? = null
    private var mDataCallback: DataFetcher.DataCallback<in InputStream>? = null

    @Volatile
    private var mCall: Call? = null


    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        val requestBuilder = Request.Builder().url(glideUrl.toStringUrl())
        for (header in glideUrl.headers) {
            requestBuilder.addHeader(header.key, header.value)
        }
        val request = requestBuilder.build()
        mDataCallback = callback
        mCall = callFactory.newCall(request)
        mCall?.enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        mDataCallback?.onLoadFailed(e)
    }

    override fun onResponse(call: Call, response: Response) {
        mResponseBody = response.body
        if (response.isSuccessful) {
            val contentLength = Preconditions.checkNotNull(mResponseBody).contentLength()
            mInputStream =
                ContentLengthInputStream.obtain(mResponseBody!!.byteStream(), contentLength)
            mDataCallback?.onDataReady(mInputStream)
        } else {
            mDataCallback?.onLoadFailed(HttpException(response.message, response.code))
        }
    }

    override fun cleanup() {
        CloseableUtils.close(mInputStream)
        CloseableUtils.close(mResponseBody)
        mDataCallback = null
    }

    override fun cancel() {
        mCall?.cancel()
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }

}