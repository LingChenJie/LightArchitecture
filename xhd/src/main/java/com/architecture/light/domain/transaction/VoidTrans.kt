package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.helper.DateHelper
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.Constant
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.domain.task.*
import com.architecture.light.domain.transaction.action.*

class VoidTrans : BaseTransaction() {

    enum class State {
        INPUT_MANAGE_PWD,
        INPUT_VOUCHER_NUMBER,
//        VOID_TASK,
    }

    override fun bindStateOnAction() {
        val actionInputManagePwd = ActionInputManagePwd {
            (it as ActionInputManagePwd).setParam(currentActivity)
        }
        bind(State.INPUT_MANAGE_PWD.name, actionInputManagePwd)
        val actionInputVoucherNumber = ActionInputVoucherNumber {
            (it as ActionInputVoucherNumber).setParam(currentActivity)
        }
        bind(State.INPUT_VOUCHER_NUMBER.name, actionInputVoucherNumber)
        gotoState(State.INPUT_MANAGE_PWD.name)
    }

    private var actionResult: ActionResult? = null

    override fun onActionResult(state: String, result: ActionResult) {
        this.actionResult = result
        val currentState = State.valueOf(state)
        val code = result.code
        val data = result.data
        when (currentState) {
            State.INPUT_MANAGE_PWD -> {
                if (code == ErrorCode.SUCCESS) {
                    gotoState(State.INPUT_VOUCHER_NUMBER.name)
                } else {
                    transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                }
            }
            State.INPUT_VOUCHER_NUMBER -> {
                if (code == ErrorCode.SUCCESS) {

                } else {
                    transEnd(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                }
            }
        }
    }

}