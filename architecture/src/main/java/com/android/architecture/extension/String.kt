package com.android.architecture.extension

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/7
 * Modify date: 2022/5/7
 * Version: 1
 */
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