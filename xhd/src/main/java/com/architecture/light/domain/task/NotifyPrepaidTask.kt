package com.architecture.light.domain.task

import com.android.architecture.helper.JsonHelper
import com.architecture.light.data.remote.bean.NotifyCollectionResponse
import com.architecture.light.data.remote.bean.NotifyPrepaidRequest
import com.architecture.light.data.remote.bean.base.RequestBean

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class NotifyPrepaidTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val request = NotifyPrepaidRequest()

        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = JsonHelper.toBean<NotifyCollectionResponse>(responseStr)
//        if (response.code == ResponseCode.SUCCESS) {
//            if (response.data != null && response.data.size > 0) {
//                param.responseCode = ErrorCode.SUCCESS
//                param.responseMessage = response.msg
//                param.searchRoomResponse = response
//            } else {
//                param.responseCode = ErrorCode.DATA_EMPTY
//                param.responseMessage = ErrorCode.getMessage(param.responseCode)
//            }
//        } else {
//            param.responseCode = response.code
//            param.responseMessage = response.msg
//        }
    }

}