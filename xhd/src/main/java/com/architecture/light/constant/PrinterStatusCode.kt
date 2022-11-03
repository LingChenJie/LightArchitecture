package com.architecture.light.constant

import com.android.architecture.extension.getString
import com.android.architecture.extension.valid
import com.architecture.light.R

object PrinterStatusCode {

    const val CODE__101 = -101
    const val CODE__1 = -1
    const val CODE__5 = -5
    const val CODE__8 = -8
    const val CODE__11 = -11
    const val CODE__99 = -99

    const val CODE_11 = 11
    const val CODE_12 = 12
    const val CODE_13 = 13
    const val CODE_14 = 14
    const val CODE_15 = 15
    const val CODE_16 = 16
    const val CODE_17 = 17
    const val CODE_18 = 18
    const val CODE_19 = 19
    const val CODE_21 = 21
    const val CODE_22 = 22
    const val CODE_23 = 23
    const val CODE_24 = 24
    const val CODE_25 = 25
    const val CODE_26 = 26
    const val CODE_27 = 27
    const val CODE_28 = 28
    const val CODE_29 = 29
    const val CODE_31 = 31
    const val CODE_32 = 32
    const val CODE_33 = 33
    const val CODE_34 = 34
    const val CODE_35 = 35
    const val CODE_36 = 36
    const val CODE_37 = 37
    const val CODE_38 = 38
    const val CODE_39 = 39
    const val CODE_41 = 41
    const val CODE_42 = 42
    const val CODE_43 = 43
    const val CODE_44 = 44
    const val CODE_45 = 45
    const val CODE_46 = 46
    const val CODE_47 = 47
    const val CODE_48 = 48
    const val CODE_49 = 49
    const val CODE_77 = 77


    private val errorCodeMap = mapOf(
        CODE__101 to "打印失败，打印机离线",
        CODE__1 to "打印失败，传入的打印机状态长度错误",
        CODE__5 to "不支持查询打印机状态",
        CODE__8 to "打印失败，打印机使用者类型内存分配失败",
        CODE__11 to "打印失败，打印机SMLLD使用授权不通过",
        CODE__99 to "打印失败，打印机未知异常错误",

        CODE_11 to "打印失败，打印机初始化",
        CODE_12 to "打印机休眠",
        CODE_13 to "打印机预热中",
        CODE_14 to "打印机粉量低",
        CODE_15 to "打印机多功能纸盒纸少",
        CODE_16 to "打印机标准纸盒纸少",
        CODE_17 to "打印机待机",
        CODE_18 to "打印失败，打印机打印中",
        CODE_19 to "打印失败，打印机致命错误",
        CODE_21 to "打印失败，打印机前盖打开",
        CODE_22 to "打印失败，打印机后盖打开",
        CODE_23 to "打印失败，打印机未安装粉盒",
        CODE_24 to "打印失败，打印机粉盒不匹配",
        CODE_25 to "打印失败，打印机粉盒用尽",
        CODE_26 to "打印失败，打印机出纸口卡纸",
        CODE_27 to "打印失败，打印机中间卡纸未排除",
        CODE_28 to "打印失败，打印机出纸口卡纸未排除",
        CODE_29 to "打印失败，打印机中间卡纸",
        CODE_31 to "打印失败，打印机双面单元卡纸",
        CODE_32 to "打印失败，打印机双面单元未安装",
        CODE_33 to "打印失败，打印机无匹配纸盒",
        CODE_34 to "打印失败，打印机纸盒未安装",
        CODE_35 to "打印失败，打印机纸盒缺纸(打印中)",
        CODE_36 to "打印失败，打印机纸盒无匹配纸张",
        CODE_37 to "打印失败，打印机进纸处卡纸",
        CODE_38 to "打印失败，打印机纸盒设定纸张与实际纸张不匹配",
        CODE_39 to "打印失败，打印机纸盒缺纸(打印机待机中)",
        CODE_41 to "打印失败，打印机未安装鼓组件",
        CODE_42 to "打印失败，打印机鼓组件不匹配",
        CODE_43 to "打印失败，打印机鼓组件用尽",
        CODE_44 to "打印失败，打印机自动进纸盒缺纸",
        CODE_45 to "打印失败，打印机手动进纸盒缺纸",
        CODE_46 to "打印失败，打印机进纸失败",
        CODE_47 to "打印失败，打印机纸型不匹配",
        CODE_48 to "打印失败，打印机双面打印出现纸型不匹配",
        CODE_49 to "打印失败，打印机纸张来源与实际进纸不匹配错误",
        CODE_77 to "打印失败，打印机未知异常状态",
    )

    fun getMessage(code: Int, message: String? = null): String {
        if (message.valid) {
            return message!!
        }
        return errorCodeMap[code] ?: getString(R.string.error_code_print_unknown)
    }

}