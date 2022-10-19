package com.architecture.light.domain.navigation

import com.android.architecture.domain.navigation.ANavigation
import com.android.architecture.domain.navigation.NavigationResult
import com.android.architecture.helper.JsonHelper
import com.android.architecture.helper.Logger
import com.architecture.light.constant.GlobalParams

abstract class BaseNavigation(listener: TransEndListener? = null) :
    ANavigation(listener) {

    val bean = GlobalParams.initNavigationBean()

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun checkExecutionConditions(): Boolean {
        return true
    }

    override fun transEnd(result: NavigationResult) {
        Logger.e("TransEnd", "TransData: ${JsonHelper.toJson(bean)}")
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