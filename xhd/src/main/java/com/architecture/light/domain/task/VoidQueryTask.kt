package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.architecture.light.data.pay.bean.TransMemo
import com.chinaums.mis.bean.RequestPojo
import org.json.JSONObject

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class VoidQueryTask : PayTask() {

    override fun onAssembly(): RequestPojo {
        val request = RequestPojo()
        request.transType = "0401"
        request.transMemo = getTransMemo()
        return request
    }

    override fun onPostExecute(payData: TransMemo.PayData) {
        if (payData.resCode == "FL" && payData.resDesc.contains("已被撤销")) {
            response.responseCode = ErrorCode.SUCCESS
            response.responseMessage = payData.resDesc
            response.payData = payData
        } else {
            response.responseCode = payData.resCode
            response.responseMessage = payData.resDesc
        }
    }

    private fun getTransMemo(): String {
        val json = JSONObject()
        json.put("orgTraceNo", param.originalVoucherNumber)
        json.put("extOrderNo", param.originalOrderNumber)
        return json.toString()
    }

}