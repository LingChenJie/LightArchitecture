package com.architecture.light.constant

import com.android.architecture.constant.LightConstant
import java.io.File

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
object Constant {

    const val IS_DEBUG = true

    val ROOT_PATH = LightConstant.SDCARD_PATH + File.separator + "XHD"
    val BILL_PATH = ROOT_PATH + File.separator + "Bill"
    val LOG_PATH = ROOT_PATH + File.separator + "Log"

}