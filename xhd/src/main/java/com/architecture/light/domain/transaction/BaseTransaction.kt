package com.architecture.light.domain.transaction

import com.android.architecture.domain.transaction.ATransaction
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.helper.Logger
import com.architecture.light.constant.GlobalParams
import com.architecture.light.data.model.db.entity.TransData
import com.google.gson.Gson

abstract class BaseTransaction(listener: TransEndListener? = null) : ATransaction(listener) {

    var transData: TransData = GlobalParams.getTransData()

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
    }

}