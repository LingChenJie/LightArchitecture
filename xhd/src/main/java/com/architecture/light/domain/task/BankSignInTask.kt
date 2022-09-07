package com.architecture.light.domain.task

import com.chinaums.mis.bean.RequestPojo
import com.chinaums.mis.bean.ResponsePojo

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class BankSignInTask : PayTask() {

    override fun onAssembly(): RequestPojo {
        val request = RequestPojo()
        request.transType = "0005"
        request.transMemo = getTransMemo()
        return request
    }

    override fun onPostExecute(response: ResponsePojo) {

    }

    private fun getTransMemo(): String {
        return "{}"
    }

}