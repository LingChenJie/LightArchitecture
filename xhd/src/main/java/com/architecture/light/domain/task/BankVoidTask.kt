package com.architecture.light.domain.task

import com.architecture.light.data.pay.bean.TransMemo
import com.chinaums.mis.bean.RequestPojo
import org.json.JSONObject

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class BankVoidTask : PayTask() {

    override fun onAssembly(): RequestPojo {
        val request = RequestPojo()
        request.transType = "0403"
        request.transMemo = getTransMemo()
        return request
    }

    override fun onPostExecute(payData: TransMemo.PayData) {
        response.payData = payData
        response.voucherNumber = payData.traceNo
    }

    private fun getTransMemo(): String {
        val json = JSONObject()
        json.put("orgTraceNo", param.originalVoucherNumber)
        json.put("UserNo", param.account)
        json.put("extOrderNo", param.orderNumber)
        return json.toString()
    }

}