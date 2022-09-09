package com.architecture.light.helper

import com.android.architecture.extension.getContext
import com.android.architecture.helper.Logger
import com.sunmi.printerx.PrinterSdk

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/3
 * Modify date: 2022/9/3
 * Version: 1
 */
object PrintHelper {

    const val TAG = "PrintHelper"
    var printer: PrinterSdk.Printer? = null

    fun init() {
        PrinterSdk.getInstance().getPrinter(getContext(), object : PrinterSdk.PrinterListen {
            override fun onDefPrinter(printer: PrinterSdk.Printer) {
                Logger.e(TAG, "onDefPrinter")
                Logger.e(TAG, "printer:$printer")
            }

            override fun onPrinters(printers: List<PrinterSdk.Printer>) {
                Logger.e(TAG, "onPrinters")
                for (printer in printers) {
                    Logger.e(TAG, "printer:$printer")
                    this@PrintHelper.printer = printer
                }
            }
        })
    }

}