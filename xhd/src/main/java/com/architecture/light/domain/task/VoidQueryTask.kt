package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.helper.Logger
import com.architecture.light.data.pay.bean.TransMemo
import com.architecture.light.helper.AmountHelper
import com.architecture.light.helper.TransHelper
import com.chinaums.mis.bean.RequestPojo
import org.json.JSONObject
import kotlin.math.abs

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
        if (payData.resCode == "00") {
            response.responseCode = ErrorCode.SUCCESS
            response.responseMessage = payData.resDesc
            response.payData = payData
            response.voucherNumber = payData.traceNo
            response.refNo = payData.refNo
            response.amount = abs(AmountHelper.convertAmount(payData.amt))
            response.transactionDate = payData.date.replace("/".toRegex(), "")
            response.transactionTime = payData.time.replace(":".toRegex(), "")
            Logger.e(
                "BankVoidTask",
                "date:${response.transactionDate}; time:${response.transactionTime}"
            )
            response.serialNumber = TransHelper.getTransactionSerialNumber(response)
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