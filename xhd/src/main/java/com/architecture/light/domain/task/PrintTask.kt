package com.architecture.light.domain.task

import android.os.ConditionVariable
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.task.BaseTask
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.remote.bean.SearchBillResponse
import com.architecture.light.helper.BillHelper

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
class PrintTask : BaseTask<TransData, TransData>() {

    override fun initParams(params: TransData) {
        response = params
    }

    override fun onPreExecute() {
    }

    override fun onExecute() {
        val list = param.searchBillResponse!!.data
        var data: SearchBillResponse.Data? = null
        for (item in list) {
            if (item.isChecked) {
                data = item
                break
            }
        }
        if (data != null) {
            BillHelper.saveBill(data, param.isRePrint)
            val cv = ConditionVariable()
            BillHelper.printBill(object : BillHelper.PrintResult {
                override fun success(msg: String) {
                    param.responseCode = ErrorCode.SUCCESS
                    param.responseMessage = msg
                    cv.open()
                }

                override fun fail(code: Int, msg: String) {
                    param.responseCode = code.toString()
                    param.responseMessage = msg
                    cv.open()
                }
            })
            cv.block()
        } else {
            param.responseCode = AppErrorCode.PRINT_DATA_NOT_FOUND
            param.responseMessage = ErrorCode.getMessage(param.responseCode)
        }
    }

}