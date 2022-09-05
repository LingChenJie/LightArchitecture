package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.extension.getString
import com.architecture.light.data.remote.ResponseCode
import com.architecture.light.data.remote.bean.SearchRoomRequest
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.data.remote.bean.base.RequestBean
import com.architecture.light.settings.LoginCache
import com.architecture.light.settings.ProjectCache
import com.architecture.light.settings.bean.LoginBean
import com.google.gson.Gson

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class SearchRoomTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val request = SearchRoomRequest()
        request.zygwGUID = param.zygwGUID
        request.projGUID = param.projGUID
        request.cardID = param.cardID//"34222419890827123X"
        request.tel = param.tel
        request.roomInfo = param.roomInfo
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = Gson().fromJson(responseStr, SearchRoomResponse::class.java)
        if (response.code == ResponseCode.SUCCESS) {
            if (response.data != null && response.data.size > 0) {
                param.responseCode = ErrorCode.SUCCESS
                param.responseMessage = response.msg
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