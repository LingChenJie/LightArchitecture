package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.helper.JsonHelper
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.remote.ResponseCode
import com.architecture.light.data.remote.bean.NotifyVoidRequest
import com.architecture.light.data.remote.bean.NotifyVoidResponse
import com.architecture.light.data.remote.bean.base.RequestBean

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class NotifyVoidTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val request = NotifyVoidRequest()
        request.serialNumber = param.originalSerialNumber
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = JsonHelper.toBean<NotifyVoidResponse>(responseStr)
        if (response.code == ResponseCode.SUCCESS) {
            param.responseMessage = ErrorCode.getMessage(AppErrorCode.TRANS_NOTIFY_SUCCESS)
        } else {
            param.responseCode = response.code
            param.responseMessage = response.msg
        }
    }

}