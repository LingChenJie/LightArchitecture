package com.architecture.light.settings.bean

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/10
 * Modify date: 2022/5/10
 * Version: 1
 */
data class PayBean(
    var posConnMode: Int = 1,
    var ip: String = "",
    var port: String = "8888",
    var comNo: String = "/dev/ttyS1",
    var boundNo: Int = 9600,
)
