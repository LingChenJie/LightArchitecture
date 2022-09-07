package com.architecture.light.domain.task

import com.chinaums.mis.bean.RequestPojo
import com.chinaums.mis.bean.ResponsePojo
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

    override fun onPostExecute(response: ResponsePojo) {

    }

    private fun getTransMemo(): String {
        val json = JSONObject()
        json.put("orgTraceNo", param.originalVoucherNumber)
        json.put("UserNo", param.account)
        json.put("extOrderNo", param.orderNumber)
        return json.toString()
    }

}