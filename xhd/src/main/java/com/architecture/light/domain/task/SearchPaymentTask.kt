package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.helper.JsonHelper
import com.architecture.light.data.remote.ResponseCode
import com.architecture.light.data.remote.bean.SearchPaymentRequest
import com.architecture.light.data.remote.bean.SearchPaymentResponse
import com.architecture.light.data.remote.bean.base.RequestBean

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class SearchPaymentTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val request = SearchPaymentRequest()
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = JsonHelper.toBean<SearchPaymentResponse>(responseStr)
        if (response.code == ResponseCode.SUCCESS) {
            if (response.data != null && response.data.size > 0) {
                param.responseCode = ErrorCode.SUCCESS
                param.responseMessage = response.msg
                val data = response.data
                val parentLevelGUID = arrayListOf<String>()
                val itemLevelGUID = arrayListOf<String>()
                for (payment in data) {
                    parentLevelGUID.add(payment.parentGUID)
                    itemLevelGUID.add(payment.feeItemGUID)
                }
                for (payment in data) {
                    if (parentLevelGUID.contains(payment.feeItemGUID)
                        && !itemLevelGUID.contains(payment.parentGUID)
                    ) {
                        payment.isParentLevel = true
                    }
                    if (!parentLevelGUID.contains(payment.feeItemGUID)) {
                        payment.isSubLevel = true
                    }
                }
                param.searchPaymentResponse = response
            } else {
                param.responseCode = ErrorCode.DATA_EMPTY
                param.responseMessage = ErrorCode.getMessage(param.responseCode)
            }
        } else {
            param.responseCode = response.code
            param.responseMessage = response.msg
        }
    }

}