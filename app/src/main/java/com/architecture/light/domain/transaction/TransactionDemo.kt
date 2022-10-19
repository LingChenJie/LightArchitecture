package com.architecture.light.domain.transaction

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.navigation.NavigationConstant
import com.android.architecture.domain.navigation.NavigationResult
import com.android.architecture.ui.page.ActivityStack
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.domain.transaction.action.ActionFirstActivity
import com.architecture.light.domain.transaction.action.ActionSecondActivity
import com.architecture.light.domain.transaction.action.ActionThirdActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class TransactionDemo() : BaseTransaction() {

    enum class State {
        FIRST_ACTIVITY,
        SECOND_ACTIVITY,
        THIRD_ACTIVITY,
    }

    override fun bindStateOnAction() {
        val actionFirstActivity = ActionFirstActivity {
        }
        bind(State.FIRST_ACTIVITY.name, actionFirstActivity)
        val actionSecondActivity = ActionSecondActivity {
        }
        bind(State.SECOND_ACTIVITY.name, actionSecondActivity)
        val actionThirdActivity = ActionThirdActivity {
        }
        bind(State.THIRD_ACTIVITY.name, actionThirdActivity)
        gotoFirstState()
    }

    override fun onActionResult(state: String, result: NavigationResult) {
        val currentState = State.valueOf(state)
        val code = result.code
        val data = result.data
        when (currentState) {
            State.FIRST_ACTIVITY -> {
                if (code == ErrorCode.SUCCESS) {
                    gotoState(State.SECOND_ACTIVITY.name)
                } else {
                    transEnd(NavigationResult(AppErrorCode.EXIT_TRANSACTION))
                }
            }
            State.SECOND_ACTIVITY -> {
                if (code == ErrorCode.SUCCESS) {
                    gotoState(State.THIRD_ACTIVITY.name)
                } else {
                    gotoState(State.FIRST_ACTIVITY.name)
                }
            }
            State.THIRD_ACTIVITY -> {
                if (code == ErrorCode.SUCCESS) {
                    gotoState(State.FIRST_ACTIVITY.name)
                } else {
                    gotoState(State.SECOND_ACTIVITY.name)
                }
            }
        }
    }

    override fun transEnd(result: NavigationResult) {
        val activity = currentActivity
        super.transEnd(result)
        when (result.code) {
            AppErrorCode.EXIT_TRANSACTION -> {
                activity.finish()
            }
        }
    }

    private fun gotoFirstState() {
        NavigationConstant.getInstance().currentActivity = ActivityStack.getInstance().topActivity
        gotoState(State.FIRST_ACTIVITY.name)
    }

}