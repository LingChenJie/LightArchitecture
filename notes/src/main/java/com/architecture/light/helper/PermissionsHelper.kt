package com.architecture.light.helper

import com.android.architecture.ui.page.ActivityStack
import com.hjq.permissions.XXPermissions

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