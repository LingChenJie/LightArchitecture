package com.architecture.light.domain.navigation

import android.content.Context
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.navigation.NavigationConstant
import com.android.architecture.domain.navigation.NavigationResult
import com.android.architecture.extension.openActivity
import com.android.architecture.helper.AppExecutors
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.domain.navigation.action.ActionFirstFragment
import com.architecture.light.domain.navigation.action.ActionSecondFragment
import com.architecture.light.domain.navigation.action.ActionThirdFragment
import com.architecture.light.domain.navigation.activity.NavigationActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class NavigationDemo(context: Context) : BaseNavigation(context) {

    enum class State {
        FIRST_FRAGMENT,
        SECOND_FRAGMENT,
        THIRD_FRAGMENT,
    }

    override fun bindStateOnAction() {
        val actionFirstFragment = ActionFirstFragment {
        }
        bind(State.FIRST_FRAGMENT.name, actionFirstFragment)
        val actionSecondFragment = ActionSecondFragment {
        }
        bind(State.SECOND_FRAGMENT.name, actionSecondFragment)
        val actionThirdFragment = ActionThirdFragment {
        }
        bind(State.THIRD_FRAGMENT.name, actionThirdFragment)
        gotoFirstState()
    }

    override fun onActionResult(state: String, result: NavigationResult) {
        val currentState = State.valueOf(state)
        val code = result.code
        val data = result.data
        when (currentState) {
            State.FIRST_FRAGMENT -> {
                if (code == ErrorCode.SUCCESS) {
                    gotoState(State.SECOND_FRAGMENT.name)
                } else {
                    transEnd(NavigationResult(AppErrorCode.EXIT_NAVIGATION))
                }
            }
            State.SECOND_FRAGMENT -> {
                if (code == ErrorCode.SUCCESS) {
                    gotoState(State.THIRD_FRAGMENT.name)
                } else {
                    gotoState(State.FIRST_FRAGMENT.name)
                }
            }
            State.THIRD_FRAGMENT -> {
                if (code == ErrorCode.SUCCESS) {
                    gotoState(State.FIRST_FRAGMENT.name)
                } else {
                    gotoState(State.SECOND_FRAGMENT.name)
                }
            }
        }
    }

    override fun transEnd(result: NavigationResult) {
        val activity = currentActivity
        super.transEnd(result)
        when (result.code) {
            AppErrorCode.EXIT_NAVIGATION -> {
                activity.finish()
            }
        }
    }

    private fun gotoFirstState() {
        context.openActivity<NavigationActivity>()
        AppExecutors.getInstance().io().execute {
            while (true) {
                if (NavigationConstant.getInstance().currentActivity != null) {
                    break;
                }
            }
            gotoState(State.FIRST_FRAGMENT.name)
        }
    }

}