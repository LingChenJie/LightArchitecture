package com.architecture.light.domain.task

import com.architecture.light.data.remote.bean.LoginRequest
import com.architecture.light.data.remote.bean.LoginResponse
import com.architecture.light.data.remote.bean.base.RequestBean
import com.google.gson.Gson

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class LogonTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val userInfo = param.userInfo!!
        val request = LoginRequest()
        request.userCode = userInfo.username
        request.password = userInfo.password
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = Gson().fromJson(responseStr, LoginResponse::class.java)

    }

}