package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.extension.toast
import com.architecture.light.data.remote.ResponseCode
import com.architecture.light.data.remote.bean.LoginRequest
import com.architecture.light.data.remote.bean.LoginResponse
import com.architecture.light.data.remote.bean.base.RequestBean
import com.architecture.light.settings.LoginCache
import com.architecture.light.settings.bean.LoginBean
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
        if (response.code == ResponseCode.SUCCESS) {
            param.responseCode = ErrorCode.SUCCESS
            param.responseMessage = response.msg
            val userInfo = param.userInfo!!
            val loginBean = LoginBean()
            loginBean.username = userInfo.username
            loginBean.password = userInfo.password
            loginBean.responseData = Gson().toJson(response.data)
            loginBean.lastLoginTime = System.currentTimeMillis().toString()
            LoginCache.saveLoginBean(loginBean)
        } else {
            param.responseCode = response.code
            param.responseMessage = response.msg
        }
    }

}