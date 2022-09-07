package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.helper.JsonHelper
import com.architecture.light.data.remote.ResponseCode
import com.architecture.light.data.remote.bean.LoginRequest
import com.architecture.light.data.remote.bean.LoginResponse
import com.architecture.light.data.remote.bean.base.RequestBean
import com.architecture.light.settings.AccountCache
import com.architecture.light.settings.ProjectCache
import com.architecture.light.settings.bean.AccountBean

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class LogonTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val userInfo = param.userInfo!!
        val request = LoginRequest()
        request.userCode = userInfo.account
        request.password = userInfo.password
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = JsonHelper.toBean<LoginResponse>(responseStr)
        if (response.code == ResponseCode.SUCCESS) {
            param.responseCode = ErrorCode.SUCCESS
            param.responseMessage = response.msg
            val userInfo = param.userInfo!!
            val accountBean = AccountBean()
            accountBean.account = userInfo.account
            accountBean.password = userInfo.password
            accountBean.username = response.data.userName
            accountBean.userGUID = response.data.userGUID
            accountBean.lastLoginTime = System.currentTimeMillis().toString()
            AccountCache.saveLoginBean(accountBean)
            ProjectCache.saveProjectList(response.data.projectList)
        } else {
            param.responseCode = response.code
            param.responseMessage = response.msg
        }
    }

}