package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.data.remote.HttpRequest
import com.android.architecture.domain.task.BaseTask
import com.android.architecture.helper.JsonHelper
import com.android.architecture.utils.NetworkUtils
import com.architecture.light.constant.Config
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.remote.bean.base.RequestBean
import com.architecture.light.data.remote.bean.base.RequestData
import com.architecture.light.utils.RequestUtils
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
abstract class HttpTask : BaseTask<TransData, TransData>() {

    private val httpRequest = HttpRequest()

    override fun initParams(params: TransData) {
        httpRequest.setTimeout(30)
        httpRequest.setUrl(Config.getBaseUrl())
        response = params
    }

    override fun onPreExecute() {
    }

    abstract fun onAssembly(): RequestBean?

    abstract fun onPostExecute(responseStr: String)

    override fun onExecute() {
        val requestBean = onAssembly()
        if (requestBean != null) {
            val isNetworkConnected = NetworkUtils.isConnected()
            if (!isNetworkConnected) {
                setErrorCode(ErrorCode.NETWORK_NO_CONNECTION)
                return
            }

            try {
                val requestData = RequestData()
                requestData.data = requestBean
                val body = JsonHelper.toJson(requestData)
                val headers = getHeaders(body)
                val response = httpRequest.postJson(headers, body)
                setErrorCode(ErrorCode.SUCCESS)
                onPostExecute(response)

            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                setErrorCode(ErrorCode.NETWORK_TIMEOUT)
                return

            } catch (e: IOException) {
                e.printStackTrace()
                setErrorCode(ErrorCode.NETWORK_ERROR)
                return

            } catch (e: Exception) {
                e.printStackTrace()
                setErrorCode(ErrorCode.NETWORK_DATA_ERROR)
                return

            }
        }
    }

    private fun getHeaders(body: String): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        headers["Authorization"] =
            RequestUtils.getOpenBodySig(Config.appId, Config.appKey, body)
        return headers
    }

    private fun setErrorCode(code: String) {
        param.responseCode = code
        param.responseMessage = ErrorCode.getMessage(code)
    }

    fun getHttpRequest(): HttpRequest {
        return httpRequest
    }

}