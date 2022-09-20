package com.architecture.light.domain.task

import com.android.architecture.helper.Logger
import com.architecture.light.data.pay.bean.TransMemo
import com.chinaums.mis.bean.RequestPojo

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class PosSignInTask : PayTask() {

    override fun onAssembly(): RequestPojo {
        val request = RequestPojo()
        request.transType = "0005"
        request.transMemo = getTransMemo()
        return request
    }

    override fun onPostExecute(payData: TransMemo.PayData) {
        Logger.d(
            "PosSignInTask",
            "merchantNo:${payData.merchantNo}; terminalNo:${payData.terminalNo}"
        )
    }

    private fun getTransMemo(): String {
        return "{}"
    }

}