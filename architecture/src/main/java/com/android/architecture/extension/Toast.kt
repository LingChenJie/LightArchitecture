package com.android.architecture.extension

import android.widget.Toast
import androidx.annotation.StringRes
import com.android.architecture.helper.MainThreadHelper
import com.android.architecture.utils.Utils

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/19
 * Modify date: 2022/5/19
 * Version: 1
 */
@JvmOverloads
fun toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    MainThreadHelper.run {
        Toast.makeText(Utils.getApp(), resId, duration).show()
    }
}

@JvmOverloads
fun toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    MainThreadHelper.run {
        Toast.makeText(Utils.getApp(), text, duration).show()
    }
}