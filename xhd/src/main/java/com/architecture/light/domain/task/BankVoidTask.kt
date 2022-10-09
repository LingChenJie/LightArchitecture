package com.architecture.light.domain.task

import android.os.SystemClock
import com.android.architecture.constant.ErrorCode
import com.android.architecture.helper.AmountHelper
import com.android.architecture.helper.DateHelper
import com.android.architecture.helper.Logger
import com.architecture.light.constant.Constant
import com.architecture.light.data.pay.bean.TransMemo
import com.architecture.light.helper.TransHelper
import com.chinaums.mis.bean.RequestPojo
import com.chinaums.mis.bean.ResponsePojo
import org.json.JSONObject
import kotlin.math.abs

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

    override fun analysisResponse(response: ResponsePojo) {
        if (Constant.IS_DEBUG && Constant.IS_SIMULATED_TRANS) {
            SystemClock.sleep(2000)
            val payData = TransMemo.PayData()
            payData.resCode = "00"
            payData.resDesc = "交易成功"
            payData.traceNo = "000012"
            payData.refNo = "123456789012"
            payData.date = DateHelper.dateString
            payData.time = DateHelper.timeString
            payData.amt = "0.02"
            onPostExecute(payData)
        } else {
            super.analysisResponse(response)
        }
    }

    private fun getTransMemo(): String {
        val json = JSONObject()
        json.put("orgTraceNo", param.originalVoucherNumber)
        //json.put("UserNo", "")
        //json.put("extOrderNo", param.orderNumber)
        return json.toString()
    }

}