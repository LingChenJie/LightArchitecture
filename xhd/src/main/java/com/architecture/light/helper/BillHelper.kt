package com.architecture.light.helper

import android.os.ConditionVariable
import android.os.SystemClock
import com.android.architecture.extension.getContext
import com.android.architecture.extension.getString
import com.android.architecture.extension.measuredView
import com.android.architecture.helper.AppExecutors
import com.android.architecture.helper.Logger
import com.android.architecture.utils.TupleUtil
import com.architecture.light.R
import com.architecture.light.constant.Constant
import com.architecture.light.constant.PrintErrorCode
import com.architecture.light.constant.PrinterStatusCode
import com.architecture.light.data.remote.bean.SearchBillResponse
import com.architecture.light.ext.toastWarn
import com.architecture.light.print.view.PreviewBillView
import com.architecture.light.utils.ImageUtils
import com.sunmi.a4printerservice.ICallback
import java.io.File

/**
 * File describe:
 * Author: SuQi
 * Create date: 9/13/22
 * Modify date: 9/13/22
 * Version: 1
 */
object BillHelper {

    const val TAG = "BillHelper"
    private const val ONE = "first.jpg"
    private const val TWO = "second.jpg"
    private const val THREE = "third.jpg"
    private const val WIDTH = 4736 / 4
    private const val HEIGHT = 6784 / 4

    fun saveBill(data: SearchBillResponse.Data, isRePrint: Boolean) {
        Logger.d(TAG, "saveBill start")
        val path = Constant.BILL_PATH
        val parentFile = File(path)
        if (!parentFile.exists()) {
            parentFile.mkdirs()
        }
        parentFile.listFiles()?.forEach { it.delete() }
        val firstPath = path + File.separator + ONE
        val secondPath = path + File.separator + TWO
        val thirdPath = path + File.separator + THREE
        val billView = PreviewBillView(getContext())
        data.printNum = getString(R.string.print_bill_first_union)
        data.customRemark =
            if (isRePrint) getString(R.string.print_bill_first_union_reprint) else ""
        billView.fullData(data)
        billView.measuredView(WIDTH, HEIGHT)
        ImageUtils.saveImage(billView, firstPath)
        data.printNum = getString(R.string.print_bill_second_union)
        data.customRemark =
            if (isRePrint) getString(R.string.print_bill_second_union_reprint) else ""
        billView.fullData(data)
        billView.measuredView(WIDTH, HEIGHT)
        ImageUtils.saveImage(billView, secondPath)
        data.printNum = getString(R.string.print_bill_third_union)
        data.customRemark =
            if (isRePrint) getString(R.string.print_bill_third_union_reprint) else ""
        billView.fullData(data)
        billView.measuredView(WIDTH, HEIGHT)
        ImageUtils.saveImage(billView, thirdPath)
        Logger.d(TAG, "saveBill end")
    }

    fun printBill(printResult: PrintResult) {
        val path = Constant.BILL_PATH
        val parentFile = File(path)
        if (!parentFile.exists()) {
            printResult.fail(-1, getString(R.string.print_bill_data_not_found))
            return
        }
        val printerService = AidlServiceFactory.instance.getA4PrinterService()
        if (printerService == null) {
            printResult.fail(-1, getString(R.string.print_bill_service_connect_fail))
            return
        }
        val status = checkPrinterStatus()
        if (status.param1 != 0) {
            printResult.fail(status.param1, status.param2)
            return
        }
        val firstPath = path + File.separator + ONE
        val secondPath = path + File.separator + TWO
        val thirdPath = path + File.separator + THREE
        AppExecutors.getInstance().single().execute {
            var result = printImage(firstPath)
            if (result.param1 == 0) {
                result = printImage(secondPath)

            } else {
                printResult.fail(result.param1, result.param2)
                return@execute
            }
            if (result.param1 == 0) {
                result = printImage(thirdPath)
            } else {
                printResult.fail(result.param1, result.param2)
                return@execute
            }
            if (result.param1 == 0) {
                SystemClock.sleep(6 * 1000)
                printResult.success(result.param2)
            } else {
                printResult.fail(result.param1, result.param2)
                return@execute
            }
        }
    }

    private fun checkPrinterStatus(): TupleUtil.Tuple<Int, String> {
        val retCode: Int
        val retMsg: String
        val printerService = AidlServiceFactory.instance.getA4PrinterService()
        val printerStatus = printerService!!.printerStatus

        if (printerStatus == PrinterStatusCode.CODE_12 ||
            printerStatus == PrinterStatusCode.CODE_13 ||
            printerStatus == PrinterStatusCode.CODE_14 ||
            printerStatus == PrinterStatusCode.CODE_15 ||
            printerStatus == PrinterStatusCode.CODE_16 ||
            printerStatus == PrinterStatusCode.CODE_17 ||
            printerStatus == PrinterStatusCode.CODE__5
        ) {
            retCode = 0
            retMsg = PrinterStatusCode.getMessage(printerStatus)
            if (printerStatus == PrinterStatusCode.CODE_14 ||
                printerStatus == PrinterStatusCode.CODE_15 ||
                printerStatus == PrinterStatusCode.CODE_16
            ) {
                toastWarn(retMsg)
            }
        } else {
            retCode = printerStatus
            retMsg = PrinterStatusCode.getMessage(printerStatus)
        }
        return TupleUtil.Tuple<Int, String>(retCode, retMsg)
    }

    private fun printImage(path: String): TupleUtil.Tuple<Int, String> {
        var retCode = -1
        var retMsg = ""
        val cv = ConditionVariable()
        val printerService = AidlServiceFactory.instance.getA4PrinterService()
        printerService?.printImage(path, 0, object : ICallback.Stub() {
            override fun onPrintResult(code: Int, msg: String) {
                Logger.d(TAG, "onPrintResult code:$code; msg:$msg")
                retCode = code
                retMsg = PrintErrorCode.getMessage(code.toString())
                cv.open()
            }
        })
        cv.block()
        return TupleUtil.Tuple<Int, String>(retCode, retMsg)
    }

    interface PrintResult {
        fun success(msg: String)
        fun fail(code: Int, msg: String)
    }

}