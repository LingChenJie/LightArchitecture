package com.architecture.light.app

import com.android.architecture.app.BaseApplication
import com.android.architecture.constant.ErrorCode
import com.android.architecture.helper.Logger
import com.architecture.light.config.ToastStyle
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.Constant
import com.hjq.toast.ToastUtils

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        Logger.e(Constant.TAG, "onCreate()")
        ErrorCode.add(AppErrorCode.errorCodeMap)
        init()
    }

    private fun init() {
        // 初始化吐司
        ToastUtils.init(this, ToastStyle())
    }


}