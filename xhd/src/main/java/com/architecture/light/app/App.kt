package com.architecture.light.app

import com.android.architecture.app.BaseApplication
import com.android.architecture.constant.ErrorCode
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.helper.AidlServiceFactory
import com.tencent.bugly.crashreport.CrashReport

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
        AidlServiceFactory.instance.init()

        CrashHandler.getInstance().init(this)
        CrashReport.initCrashReport(this, "0b4c7db09d", true)
    }

    override fun onTerminate() {
        super.onTerminate()
        AidlServiceFactory.instance.release()
    }

}