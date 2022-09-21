package com.architecture.light.helper

import android.os.ConditionVariable
import com.android.architecture.extension.getContext
import com.android.architecture.extension.measuredView
import com.android.architecture.helper.AppExecutors
import com.android.architecture.helper.Logger
import com.android.architecture.utils.TupleUtil
import com.architecture.light.constant.Constant
import com.architecture.light.data.remote.bean.SearchBillResponse
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

    fun saveBill(data: SearchBillResponse.DataBean, isRePrint: Boolean) {
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
        data.printNum = "第一联"
        data.customRemark = if (isRePrint) "存根联-重打印" else ""
        billView.fullData(data)
        billView.measuredView(WIDTH, HEIGHT)
        ImageUtils.saveImage(billView, firstPath)
        data.printNum = "第二联"
        data.customRemark = if (isRePrint) "客户联-重打印" else ""
        billView.fullData(data)
        billView.measuredView(WIDTH, HEIGHT)
        ImageUtils.saveImage(billView, secondPath)
        data.printNum = "第三联"
        data.customRemark = if (isRePrint) "记账联-重打印" else ""
        billView.fullData(data)
        billView.measuredView(WIDTH, HEIGHT)
        ImageUtils.saveImage(billView, thirdPath)
        Logger.d(TAG, "saveBill end")
    }

    fun printBill(printResult: PrintResult) {
        val path = Constant.BILL_PATH
        val parentFile = File(path)
        if (!parentFile.exists()) {
            printResult.fail(-1, "打印数据不存在")
            return
        }
        val printerService = AidlServiceFactory.instance.getA4PrinterService()
        if (printerService == null) {
            printResult.fail(-1, "打印服务连接失败")
            return
        }
        val firstPath = path + File.separator + ONE
        val secondPath = path + File.separator + TWO
        val thirdPath = path + File.separator + THREE
        AppExecutors.getInstance().single().execute {
            var result = printImage(firstPath)
            if (result.a == 0) {
                result = printImage(secondPath)

            } else {
                printResult.fail(result.a, result.b)
                return@execute
            }
            if (result.a == 0) {
                result = printImage(thirdPath)
            } else {
                printResult.fail(result.a, result.b)
                return@execute
            }
            if (result.a == 0) {
                printResult.success()
            } else {
                printResult.fail(result.a, result.b)
                return@execute
            }
        }
    }

    private fun printImage(path: String): TupleUtil.Tuple<Int, String> {
        var retCode = -1
        var retMsg = ""
        val cv = ConditionVariable()
        val printerService = AidlServiceFactory.instance.getA4PrinterService()
        printerService?.printImage(path, 0, object : ICallback.Stub() {
            override fun onPrintResult(code: Int, msg: String) {
                retCode = code
                retMsg = msg
                cv.open()
            }
        })
        cv.block()
        return TupleUtil.Tuple<Int, String>(retCode, retMsg)
    }

    interface PrintResult {
        fun success()
        fun fail(code: Int, msg: String)
    }

}