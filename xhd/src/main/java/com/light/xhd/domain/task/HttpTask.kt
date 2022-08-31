package com.light.xhd.domain.task

import com.android.architecture.data.remote.HttpRequest
import com.android.architecture.domain.task.BaseTask
import com.google.gson.Gson
import com.light.xhd.constant.Config
import com.light.xhd.data.remote.bean.base.RequestBean
import com.light.xhd.data.remote.bean.base.RequestData
import com.light.xhd.utils.Utils
import java.net.SocketTimeoutException

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
abstract class HttpTask<R> : BaseTask<RequestBean, R>() {

    val httpRequest = HttpRequest()
    lateinit var requestJson: String

    override fun initParams(requestBean: RequestBean) {
        httpRequest.setTimeout(30)
        httpRequest.setUrl(Config.getBaseUrl())
        val requestData = RequestData()
        requestData.data = requestBean
        requestJson = Gson().toJson(requestData)
    }

    override fun onPreExecute() {
    }

    override fun onExecute() {
        try {
            val headers = mutableMapOf<String, String>()
            headers["Authorization"] =
                Utils.getOpenBodySig(Config.appId, Config.appKey, requestJson)
            val response = httpRequest.postJson(headers, requestJson)
            unpack(response)
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract fun unpack(response: String)

}