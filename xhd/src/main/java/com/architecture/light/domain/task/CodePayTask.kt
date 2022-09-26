package com.architecture.light.domain.task

import android.os.SystemClock
import com.android.architecture.constant.ErrorCode
import com.android.architecture.helper.DateHelper
import com.android.architecture.helper.Logger
import com.architecture.light.constant.Constant
import com.architecture.light.data.pay.bean.TransMemo
import com.architecture.light.helper.AmountHelper
import com.architecture.light.helper.TransHelper
import com.chinaums.mis.bean.RequestPojo
import com.chinaums.mis.bean.ResponsePojo
import org.json.JSONObject

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class CodePayTask : PayTask() {

    override fun onAssembly(): RequestPojo {
        val request = RequestPojo()
        request.transType = "0404"
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
            response.transactionDate = payData.date.replace("/".toRegex(), "")
            response.transactionTime = payData.time.replace(":".toRegex(), "")
            Logger.e(
                "CodePayTask",
                "date:${response.transactionDate}; time:${response.transactionTime}"
            )
            response.serialNumber = TransHelper.getTransactionSerialNumber(response)
        } else {
            response.responseCode = payData.resCode
            response.responseMessage = payData.resDesc
        }
    }

    override fun analysisResponse(response: ResponsePojo) {
        if (Constant.IS_DEBUG && Constant.IS_SIMULATED_TRANS) {
            SystemClock.sleep(2000)
            val payData = TransMemo.PayData()
            payData.resCode = "00"
            payData.resDesc = "交易成功"
            payData.traceNo = "000011"
            payData.refNo = "123456789011"
            payData.date = DateHelper.dateString
            payData.time = DateHelper.timeString
            onPostExecute(payData)
        } else {
            super.analysisResponse(response)
        }
    }

    private fun getTransMemo(): String {
        val json = JSONObject()
        json.put("amt", AmountHelper.yuan2Fen12(param.amount))
        json.put("extOrderNo", param.orderNumber)
        json.put("settleAccount", param.bankAccount)
        json.put("propertyTag", "CMDC")
        json.put("appId", "c0c83bd5025141e6a84e17174c4d5465")
        return json.toString()
    }

}