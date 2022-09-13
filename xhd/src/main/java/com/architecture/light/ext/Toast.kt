package com.architecture.light.ext

import android.view.Gravity
import android.widget.Toast
import com.android.architecture.helper.MainThreadHelper
import com.architecture.light.R
import com.hjq.toast.ToastUtils

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/19
 * Modify date: 2022/5/19
 * Version: 1
 */
@JvmOverloads
fun toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    MainThreadHelper.run {
        ToastUtils.setView(R.layout.toast_warn_view)
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 260)
        ToastUtils.show(text)
    }
}

@JvmOverloads
fun toastSucc(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    MainThreadHelper.run {
        ToastUtils.setView(R.layout.toast_info_view)
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 260)
        ToastUtils.show(text)
    }
}

@JvmOverloads
fun toastWarn(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    MainThreadHelper.run {
        ToastUtils.setView(R.layout.toast_warn_view)
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 260)
        ToastUtils.show(text)
    }
}