package com.architecture.light.domain.task

import com.chinaums.mis.bean.RequestPojo
import com.chinaums.mis.bean.ResponsePojo
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

    override fun onPostExecute(response: ResponsePojo) {

    }

    private fun getTransMemo(): String {
        val json = JSONObject()
        json.put("amt", param.amount.toString())
        json.put("extOrderNo", param.orderNumber)
        json.put("settleAccount", param.bankAccount)
        json.put("bCardType", "0")
        json.put("isRealAuth", "2")
        json.put("propertyTag", "CMDC")
        return json.toString()
    }

}