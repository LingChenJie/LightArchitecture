package com.architecture.light.constant

import com.android.architecture.constant.LightConstant
import com.architecture.light.BuildConfig
import java.io.File

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
object Constant {

    const val IS_DEBUG = BuildConfig.IS_DEBUG
    const val IS_SIMULATED_TRANS = BuildConfig.IS_SIMULATED_TRANS

    val ROOT_PATH = LightConstant.SDCARD_PATH + File.separator + "XHD"
    val BILL_PATH = ROOT_PATH + File.separator + "Bill"
    val LOG_PATH = ROOT_PATH + File.separator + "Log"

}