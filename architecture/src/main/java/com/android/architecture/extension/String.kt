package com.android.architecture.extension

import androidx.annotation.StringRes
import com.android.architecture.utils.AppUtils

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/7
 * Modify date: 2022/5/7
 * Version: 1
 */
fun getString(@StringRes resId: Int) = AppUtils.getApp().getString(resId)

val String?.empty: Boolean
    get() {
        if (this == null) {
            return true
        }
        return isEmpty()
    }

val String?.valid: Boolean
    get() {
        if (this == null) {
            return false
        }
        return isNotEmpty()
    }