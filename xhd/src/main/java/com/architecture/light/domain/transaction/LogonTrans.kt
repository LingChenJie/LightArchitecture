package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.toast
import com.architecture.light.data.model.db.entity.UserInfo
import com.architecture.light.domain.task.LogonTask
import com.architecture.light.domain.transaction.action.ActionInputLoginInfo
import com.architecture.light.domain.transaction.action.ActionTask


class LogonTrans : BaseTransaction() {

    enum class State {
        INPUT_LOGIN_INFO,
        TASK_EXECUTE
    }

    override fun bindStateOnAction() {
        val actionInputLoginInfo = ActionInputLoginInfo {
            (it as ActionInputLoginInfo).setParam(currentActivity)
        }
        bind(State.INPUT_LOGIN_INFO.name, actionInputLoginInfo)
        val actionTask = ActionTask {
            (it as ActionTask).setParam(LogonTask(), transData, currentActivity)
        }
        bind(State.TASK_EXECUTE.name, actionTask)
        gotoState(State.INPUT_LOGIN_INFO.name)
    }

    override fun onActionResult(state: String, result: ActionResult) {
        val currentState = State.valueOf(state)
        val code = result.code
        if (code != ErrorCode.SUCCESS) {
            transEnd(result)
            return
        }
        when (currentState) {
            State.INPUT_LOGIN_INFO -> {
                val loginInfo = result.data as ActionInputLoginInfo.LoginInfo
                val userInfo = UserInfo()
                userInfo.username = loginInfo.username
                userInfo.password = loginInfo.password
                transData.userInfo = userInfo
                gotoState(State.TASK_EXECUTE.name)
            }
            State.TASK_EXECUTE -> {
                toast(transData.responseMessage + "[" + transData.responseCode + "]")
                if (transData.responseCode != ErrorCode.SUCCESS) {
                    gotoState(State.INPUT_LOGIN_INFO.name)
                } else {
                    transEnd(result)
                }
            }
        }
    }

}