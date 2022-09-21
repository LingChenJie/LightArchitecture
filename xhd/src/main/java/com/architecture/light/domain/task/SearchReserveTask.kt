package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.helper.JsonHelper
import com.architecture.light.data.remote.ResponseCode
import com.architecture.light.data.remote.bean.SearchReserveRequest
import com.architecture.light.data.remote.bean.SearchReserveResponse
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.data.remote.bean.base.RequestBean

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class SearchReserveTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val request = SearchReserveRequest()
        request.zygwGUID = param.zygwGUID
        request.projGUID = param.projGUID
        request.cardID = param.cardID
        request.tel = param.tel
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = JsonHelper.toBean<SearchReserveResponse>(responseStr)
        if (response.code == ResponseCode.SUCCESS) {
            if (response.data != null && response.data.size > 0) {
                param.responseCode = ErrorCode.SUCCESS
                param.responseMessage = response.msg
                param.searchReserveResponse = response
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