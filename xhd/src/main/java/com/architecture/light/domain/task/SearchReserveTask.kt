package com.architecture.light.domain.task

import com.architecture.light.data.remote.bean.SearchReserveRequest
import com.architecture.light.data.remote.bean.SearchReserveResponse
import com.architecture.light.data.remote.bean.base.RequestBean
import com.google.gson.Gson

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class SearchReserveTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val request = SearchReserveRequest()
        request.zygwGUID = param.zygwGUID
        request.projGUID = param.projGUID
        request.cardID = "999888777666"
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = Gson().fromJson(responseStr, SearchReserveResponse::class.java)
    }

}