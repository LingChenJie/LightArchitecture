package com.architecture.light.settings.bean

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/10
 * Modify date: 2022/5/10
 * Version: 1
 */
data class LoginBean(
    var account: String = "",
    var password: String = "",
    var username: String = "",
    var userGUID: String = "",
    var projGUID: String = "",
    var lastLoginTime: String = ""
)
