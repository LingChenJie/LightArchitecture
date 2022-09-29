package com.android.architecture.constant

import android.os.Build
import android.os.Environment
import com.android.architecture.extension.getContext

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/1
 * Modify date: 2022/8/1
 * Version: 1
 */
object LightConstant {

    const val TAG = "LightArchitecture"

    val SDCARD_PATH: String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            getContext().filesDir.absolutePath
        else Environment.getExternalStorageDirectory().absolutePath

    val FILE_PATH: String = getContext().filesDir.absolutePath
    val CACHE_PATH: String = getContext().cacheDir.absolutePath

}