package com.architecture.light.app

import com.android.architecture.app.BaseApplication
import com.android.architecture.constant.ErrorCode
import com.architecture.light.constant.AppErrorCode

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        ErrorCode.add(AppErrorCode.errorCodeMap)
    }

}