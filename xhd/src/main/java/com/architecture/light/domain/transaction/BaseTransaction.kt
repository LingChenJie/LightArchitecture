package com.architecture.light.domain.transaction

import com.android.architecture.domain.transaction.ATransaction
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.helper.Logger
import com.android.architecture.ui.page.ActivityStack
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.GlobalParams
import com.architecture.light.ui.page.activity.MainActivity
import com.google.gson.Gson

abstract class BaseTransaction(listener: TransEndListener? = null) : ATransaction(listener) {

    val transData = GlobalParams.getTransData()

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun checkExecutionConditions(): Boolean {
        return true
    }

    override fun transEnd(result: ActionResult) {
        Logger.e("TransEnd", "TransData: ${Gson().toJson(transData)}")
        super.transEnd(result)
        GlobalParams.resetTransData()
        when (result.code) {
            AppErrorCode.BACK_TO_MAIN_PAGE -> {
                ActivityStack.getInstance().removeAllButFew(MainActivity::class.java)
            }
            AppErrorCode.EXIT_APP -> {
                ActivityStack.getInstance().removeAll()
            }
        }
    }

}