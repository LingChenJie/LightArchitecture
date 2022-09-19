package com.architecture.light.domain.task

import com.architecture.light.data.pay.bean.TransMemo
import com.architecture.light.helper.AmountHelper
import com.chinaums.mis.bean.RequestPojo
import org.json.JSONObject

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class BankPayTask : PayTask() {

    override fun onAssembly(): RequestPojo {
        val request = RequestPojo()
        request.transType = "0402"
        request.transMemo = getTransMemo()
        return request
    }

    override fun onPostExecute(payData: TransMemo.PayData) {
        response.payData = payData
        response.voucherNumber = payData.traceNo
    }

    private fun getTransMemo(): String {
        val json = JSONObject()
        json.put("amt", AmountHelper.yuan2Fen12(param.amount))
        json.put("extOrderNo", param.orderNumber)
        json.put("settleAccount", param.bankAccount)
        json.put("bCardType", "0")
        json.put("isRealAuth", "2")
        json.put("propertyTag", "CMDC")
        json.put("appId", "c0c83bd5025141e6a84e17174c4d5465")
        return json.toString()
    }

}