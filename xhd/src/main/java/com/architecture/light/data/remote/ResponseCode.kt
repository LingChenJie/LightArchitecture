package com.architecture.light.data.remote

import androidx.lifecycle.Transformations.map
import com.android.architecture.R
import com.android.architecture.extension.getString
import com.android.architecture.extension.valid

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
object ResponseCode {
    const val SUCCESS = "00000000"
    const val F1000 = "1000"
    const val F00001001 = "00001001"
    const val F00001002 = "00001002"
    const val F00001003 = "00001003"
    const val F00001004 = "00001004"
    const val F00001005 = "00001005"
    const val F00001006 = "00001006"
    const val F00001015 = "00001015"

    private val errorCodeMap = mapOf(
        SUCCESS to getString(R.string.error_code_success),
        F1000 to "认证失败",
        F00001001 to "一般业务错误，参看错误信息",
        F00001002 to "登录超时",
        F00001003 to "请求失败，一般是服务端无法连接，或者erp系统出现异常",
        F00001004 to "验签失败",
        F00001005 to "参数错误",
        F00001006 to "终端序号没在erp注册",
        F00001015 to "接口返回空报文",

        )

    fun getMessage(code: String): String {
        return errorCodeMap[code] ?: getString(R.string.error_code_undefined)
    }
}