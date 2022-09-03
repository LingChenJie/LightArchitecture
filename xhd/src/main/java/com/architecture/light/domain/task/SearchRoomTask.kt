package com.architecture.light.domain.task

import com.architecture.light.data.remote.bean.SearchRoomRequest
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.data.remote.bean.base.RequestBean
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
        request.cardID = "34222419890827123X"
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = Gson().fromJson(responseStr, SearchRoomResponse::class.java)
    }

}