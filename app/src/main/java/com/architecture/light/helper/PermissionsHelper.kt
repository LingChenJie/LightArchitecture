package com.architecture.light.helper

import com.android.architecture.ui.page.ActivityStack
import com.hjq.permissions.XXPermissions

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/5
 * Modify date: 2022/9/5
 * Version: 1
 */
object PermissionsHelper {

    fun requirePermissions(
        vararg permissions: String,
        call: (() -> Unit)? = null
    ) {
        val currentActivity = ActivityStack.getInstance().topActivity
        XXPermissions.with(currentActivity)
            .permission(permissions)
            .request(object : PermissionCallback() {
                override fun onGranted(permissions: List<String>, all: Boolean) {
                    if (all) {
                        call?.invoke()
                    }
                }
            })
    }

}