package com.android.architecture.extension

import android.widget.Toast
import androidx.annotation.StringRes
import com.android.architecture.helper.MainThreadHelper
import com.hjq.toast.ToastUtils

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
        //Toast.makeText(AppUtils.getApp(), resId, duration).show()
        ToastUtils.show(resId)
    }
}

@JvmOverloads
fun toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    MainThreadHelper.run {
        //Toast.makeText(AppUtils.getApp(), text, duration).show()
        ToastUtils.show(text)
    }
}