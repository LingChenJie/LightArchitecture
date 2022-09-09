package com.architecture.light.domain.task

import com.android.architecture.helper.JsonHelper
import com.architecture.light.data.remote.bean.SearchBillRequest
import com.architecture.light.data.remote.bean.SearchBillResponse
import com.architecture.light.data.remote.bean.base.RequestBean

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class SearchBillTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val request = SearchBillRequest()
        request.cardID = param.cardID
        request.tel = param.tel
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = JsonHelper.toBean<SearchBillResponse>(responseStr)
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