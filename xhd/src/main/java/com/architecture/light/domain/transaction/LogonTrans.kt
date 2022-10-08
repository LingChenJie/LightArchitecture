package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.model.db.entity.UserInfo
import com.architecture.light.data.remote.bean.LoginResponse
import com.architecture.light.domain.task.LogonTask
import com.architecture.light.domain.transaction.action.ActionHttpTask
import com.architecture.light.domain.transaction.action.ActionInputBillRecipient
import com.architecture.light.domain.transaction.action.ActionInputLoginInfo
import com.architecture.light.domain.transaction.action.ActionProjectChoose
import com.architecture.light.settings.AccountCache
import com.architecture.light.settings.ProjectCache


class LogonTrans : BaseTransaction() {

    enum class State {
        INPUT_LOGIN_INFO,
        LOGON_TASK,
        PROJECT_CHOOSE,
        INPUT_BILL_RECIPIENT,
    }

    override fun bindStateOnAction() {
        val actionInputLoginInfo = ActionInputLoginInfo {
        }
        bind(State.INPUT_LOGIN_INFO.name, actionInputLoginInfo)
        val actionLogonTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(LogonTask(), transData)
        }
        bind(State.LOGON_TASK.name, actionLogonTask)
        val actionProjectChoose = ActionProjectChoose {
        }
        bind(State.PROJECT_CHOOSE.name, actionProjectChoose)
        val actionInputBillRecipient = ActionInputBillRecipient {
        }
        bind(State.INPUT_BILL_RECIPIENT.name, actionInputBillRecipient)
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
                    userInfo.account = loginInfo.username
                    userInfo.password = loginInfo.password
                    transData.userInfo = userInfo
                    gotoState(State.LOGON_TASK.name)
                } else {
                    transEnd(result)
                }
            }
            State.LOGON_TASK -> {
                if (code == ErrorCode.SUCCESS) {
                    if (transData.responseCode == ErrorCode.SUCCESS) {
                        gotoState(State.PROJECT_CHOOSE.name)
                    } else {
                        toastTransResult()
                        gotoState(State.INPUT_LOGIN_INFO.name)
                    }
                } else {
                    toastActionResult(result)
                    gotoState(State.INPUT_LOGIN_INFO.name)
                }
            }
            State.PROJECT_CHOOSE -> {
                if (code == ErrorCode.SUCCESS) {
                    val project = data as LoginResponse.Data.Project
                    ProjectCache.saveProject(project)
                    gotoState(State.INPUT_BILL_RECIPIENT.name)
                } else {
                    gotoState(State.INPUT_LOGIN_INFO.name)
                }
            }
            State.INPUT_BILL_RECIPIENT -> {
                if (code == ErrorCode.SUCCESS) {
                    val billRecipientInfo = data as ActionInputBillRecipient.BillRecipientInfo
                    AccountCache.saveBillRecipient(billRecipientInfo.account)
                    AccountCache.saveLoginStatus(true)
                    transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                } else {
                    gotoState(State.PROJECT_CHOOSE.name)
                }
            }
        }
    }

}