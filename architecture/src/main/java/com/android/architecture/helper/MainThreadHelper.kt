package com.android.architecture.helper

import android.os.Looper

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/19
 * Modify date: 2022/7/19
 * Version: 1
 */
object MainThreadHelper {

    fun run(call: () -> Unit) {
        if (isMainThread()) {
            call.invoke()
        } else {
            AppExecutors.getInstance().main().execute(call)
        }
    }

    fun isMainThread(): Boolean {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return true
        }
        return false
    }

}