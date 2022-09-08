package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.task.BaseTask
import com.android.architecture.helper.Logger
import com.android.architecture.utils.NetworkUtils
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.settings.PayCache
import com.chinaums.mis.bank.BankDAO
import com.chinaums.mis.bean.RequestPojo
import com.chinaums.mis.bean.ResponsePojo
import com.chinaums.mis.bean.TransCfx

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
abstract class PayTask : BaseTask<TransData, TransData>() {

    private val transCfx = TransCfx()
    private val bankDAO = BankDAO()

    override fun initParams(params: TransData) {
        transCfx.posConnMode = PayCache.getPosConnMode()
        transCfx.ip = PayCache.getIp()
        transCfx.devPath = PayCache.getComNo()
        transCfx.baudRate = PayCache.getBoundNo()
        response = params
    }

    override fun onPreExecute() {
    }

    abstract fun onAssembly(): RequestPojo?

    abstract fun onPostExecute(response: ResponsePojo)

    override fun onExecute() {
        val requestBean = onAssembly()
        if (requestBean != null) {
            Logger.d("PayTask", "Request: " + requestBean.transMemo)
            val isNetworkConnected = NetworkUtils.isConnected()
            if (!isNetworkConnected && PayCache.getPosConnMode() == 1) {
                setErrorCode(ErrorCode.NETWORK_NO_CONNECTION)
                return
            }

            try {
                bankDAO.getCallBack { stateCode, stateTips ->
                    Logger.d("PayTask", "stateCode:$stateCode; stateTips:$stateTips")
                }
                val response = bankDAO.bankall(transCfx, requestBean)
                Logger.d("PayTask", response.toString())
                onPostExecute(response)

            } catch (e: Exception) {
                e.printStackTrace()
                setErrorCode(AppErrorCode.PAY_DATA_ERROR)
                return

            }
        }
    }

    private fun setErrorCode(code: String) {
        param.responseCode = code
        param.responseMessage = ErrorCode.getMessage(code)
    }

}