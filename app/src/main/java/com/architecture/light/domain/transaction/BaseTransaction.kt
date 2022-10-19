package com.architecture.light.domain.transaction

import com.android.architecture.domain.navigation.ANavigation
import com.android.architecture.domain.navigation.NavigationResult
import com.architecture.light.constant.GlobalParams

abstract class BaseTransaction(listener: TransEndListener? = null) :
    ANavigation(listener) {

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun checkExecutionConditions(): Boolean {
        return true
    }

    override fun transEnd(result: NavigationResult) {
        super.transEnd(result)
        GlobalParams.resetNavigationBean()
//        when (result.code) {
//            AppErrorCode.BACK_TO_MAIN_PAGE -> {
//                ActivityStack.getInstance().removeAllButFew(MainActivity::class.java)
//            }
//            AppErrorCode.EXIT_APP -> {
//                ActivityStack.getInstance().removeAll()
//            }
//        }
    }

}