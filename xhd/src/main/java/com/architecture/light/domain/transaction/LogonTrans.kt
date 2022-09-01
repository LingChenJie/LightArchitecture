package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult


class LogonTrans : BaseTransaction() {

    enum class State {
        INPUT_LOGIN_INFO,
        TASK_EXECUTE
    }

    override fun bindStateOnAction() {

    }

    override fun onActionResult(state: String, result: ActionResult) {
        val currentState = State.valueOf(state)
        val code = result.code
        if (ErrorCode.SUCCESS != code) {
            transEnd(result)
            return
        }
        when (currentState) {
            State.INPUT_LOGIN_INFO -> {

            }
            State.TASK_EXECUTE -> {

            }
        }

    }


}