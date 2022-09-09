package com.architecture.light.domain.task

import android.os.ConditionVariable
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.task.BaseTask
import com.android.architecture.helper.Logger
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.helper.PrintHelper
import com.sunmi.printerx.api.PrintResult

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
abstract class PrintTask : BaseTask<TransData, TransData>() {

    override fun initParams(params: TransData) {
        response = params
    }

    override fun onPreExecute() {
    }

    override fun onExecute() {
        if (PrintHelper.printer == null) {
            PrintHelper.init()
            setErrorCode(AppErrorCode.PRINTER_NOT_FOUND)
            return
        } else {
            try {
                val printer = PrintHelper.printer!!
                val fileApi = printer.fileApi()
                val cv = ConditionVariable()
                fileApi.printFile("", object : PrintResult() {
                    override fun onResult(resultCode: Int, message: String) {
                        Logger.d("PrintTask", "resultCode:$resultCode; message:$message")
                        param.responseCode = ErrorCode.SUCCESS
                        param.responseMessage = message
                        cv.open()
                    }
                })
                cv.block()
            } catch (e: Exception) {
                e.printStackTrace()
                setErrorCode(AppErrorCode.PRINT_EXCEPTION)
            }
        }
    }

    private fun setErrorCode(code: String) {
        param.responseCode = code
        param.responseMessage = ErrorCode.getMessage(code)
    }

}