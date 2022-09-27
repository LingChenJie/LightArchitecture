package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.task.BaseTask
import com.android.architecture.extension.empty
import com.android.architecture.helper.JsonHelper
import com.android.architecture.helper.Logger
import com.android.architecture.utils.NetworkUtils
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.PosConnMode
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.pay.bean.TransMemo
import com.architecture.light.helper.PayRequest
import com.architecture.light.settings.PayCache
import com.chinaums.mis.bean.RequestPojo
import com.chinaums.mis.bean.ResponsePojo

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
abstract class PayTask : BaseTask<TransData, TransData>() {

    override fun initParams(params: TransData) {
        response = params
    }

    override fun onPreExecute() {
    }

    abstract fun onAssembly(): RequestPojo?

    abstract fun onPostExecute(payData: TransMemo.PayData)

    override fun onExecute() {
        val requestBean = onAssembly()
        if (requestBean != null) {
            val isNetworkConnected = NetworkUtils.isConnected()
            val posConnMode = PayCache.getPosConnMode()
            if (!isNetworkConnected && (posConnMode == PosConnMode.WIFI_SO || posConnMode == PosConnMode.WIFI)) {
                setErrorCode(ErrorCode.NETWORK_NO_CONNECTION)
                return
            }
            if ((posConnMode == PosConnMode.WIFI_SO || posConnMode == PosConnMode.WIFI) && PayCache.getIp().empty) {
                setErrorCode(AppErrorCode.PAY_PARAMS_NOT_SET)
                return
            }

            try {
                requestBean.erpId = ""
                Logger.d("PayTask", "Request =>>> $requestBean")
                val response = PayRequest().execute(requestBean)
                Logger.d("PayTask", "Response =>>> $response")
                setErrorCode(ErrorCode.SUCCESS)
                analysisResponse(response)

            } catch (e: Exception) {
                e.printStackTrace()
                setErrorCode(AppErrorCode.PAY_DATA_ERROR)
                return

            }
        } else {
            setErrorCode(AppErrorCode.PAY_DATA_ERROR)
            return
        }
    }

    open fun analysisResponse(response: ResponsePojo) {
        if (response.rspCode == "00") {
            val transMemo = JsonHelper.toBean<TransMemo>(response.transMemo)
            if (transMemo.resultCode == "0") {
                val payData = transMemo.transData
                if (payData.resCode == "00") {
                    onPostExecute(payData)
                } else {
                    param.responseCode = payData.resCode
                    param.responseMessage = payData.resDesc
                }
            } else {
                param.responseCode = transMemo.resultCode
                param.responseMessage = transMemo.resultMsg
            }
        } else if (response.rspCode == "E5") {
            param.responseCode = AppErrorCode.PAY_TIMEOUT
            param.responseMessage = ErrorCode.getMessage(AppErrorCode.PAY_TIMEOUT)
        } else {
            param.responseCode = response.rspCode
            param.responseMessage = response.rspChin.trim()
        }
    }

    private fun setErrorCode(code: String) {
        param.responseCode = code
        param.responseMessage = ErrorCode.getMessage(code)
    }

}