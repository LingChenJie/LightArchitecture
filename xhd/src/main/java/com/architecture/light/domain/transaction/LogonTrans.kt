package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.model.db.entity.UserInfo
import com.architecture.light.domain.task.LogonTask
import com.architecture.light.domain.transaction.action.ActionInputLoginInfo
import com.architecture.light.domain.transaction.action.ActionProjectChoose
import com.architecture.light.domain.transaction.action.ActionTask
import com.architecture.light.settings.AccountCache


class LogonTrans : BaseTransaction() {

    enum class State {
        INPUT_LOGIN_INFO,
        LOGON_TASK,
        PROJECT_CHOOSE,
    }

    override fun bindStateOnAction() {
        val actionInputLoginInfo = ActionInputLoginInfo {
            (it as ActionInputLoginInfo).setParam(currentActivity)
        }
        bind(State.INPUT_LOGIN_INFO.name, actionInputLoginInfo)
        val actionLogonTask = ActionTask {
            (it as ActionTask).setParam(LogonTask(), transData, currentActivity)
        }
        bind(State.LOGON_TASK.name, actionLogonTask)
        val actionProjectChoose = ActionProjectChoose {
            (it as ActionProjectChoose).setParam(currentActivity)
        }
        bind(State.PROJECT_CHOOSE.name, actionProjectChoose)
        gotoState(State.INPUT_LOGIN_INFO.name)
    }

    override fun onActionResult(state: String, result: ActionResult) {
        val currentState = State.valueOf(state)
        val code = result.code
        val data = result.data
        when (currentState) {
            State.INPUT_LOGIN_INFO -> {
                if (code == ErrorCode.SUCCESS) {
                    val loginInfo = data as ActionInputLoginInfo.LoginInfo
                    val userInfo = UserInfo()
                    userInfo.account = loginInfo.account
                    userInfo.password = loginInfo.password
                    transData.userInfo = userInfo
                    gotoState(State.LOGON_TASK.name)
                } else {
                    transEnd(result)
                }
            }
            State.LOGON_TASK -> {
                if (code == ErrorCode.SUCCESS) {
                    toastTransResult()
                    if (transData.responseCode == ErrorCode.SUCCESS) {
                        gotoState(State.PROJECT_CHOOSE.name)
                    } else {
                        gotoState(State.INPUT_LOGIN_INFO.name)
                    }
                } else {
                    toastActionResult(result)
                    gotoState(State.INPUT_LOGIN_INFO.name)
                }
            }
            State.PROJECT_CHOOSE -> {
                if (code == ErrorCode.SUCCESS) {
                    AccountCache.saveLoginStatus(true)
                    transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                } else {
                    gotoState(State.INPUT_LOGIN_INFO.name)
                }
            }
        }
    }

}