package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.extension.valid
import com.android.architecture.helper.JsonHelper
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.remote.ResponseCode
import com.architecture.light.data.remote.bean.SearchBillRequest
import com.architecture.light.data.remote.bean.SearchBillResponse
import com.architecture.light.data.remote.bean.base.RequestBean
import com.architecture.light.helper.TransHelper

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class SearchBillTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val request = SearchBillRequest()
        request.cardID = param.cardID
        request.tel = param.tel
        if (param.serialNumber.valid) {
            request.serialNumber = TransHelper.getTransactionSerialNumber(param)
        }
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = JsonHelper.toBean<SearchBillResponse>(responseStr)
        if (response.code == ResponseCode.SUCCESS) {
            if (response.data != null && response.data.size > 0) {
                param.responseCode = ErrorCode.SUCCESS
                param.responseMessage = response.msg
                if (response.data.size == 1) {
                    response.data[0].isChecked = true
                }
                param.searchBillResponse = response
            } else {
                param.responseCode = AppErrorCode.PRINT_DATA_NOT_FOUND
                param.responseMessage = ErrorCode.getMessage(param.responseCode)
            }
        } else {
            param.responseCode = response.code
            param.responseMessage = response.msg
        }
    }

}