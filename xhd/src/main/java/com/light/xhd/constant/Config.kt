package com.light.xhd.constant

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
object Config {

    private const val TEST_URL = "https://test-api-open.chinaums.com/v1/fgj/front"
    private const val RELEASE_URL = "https://api-mop.chinaums.com/v1/fgj/front"

    const val appId = "10037e6f67593b5e016763785da90000"
    const val appKey = "2bf0ae27f34e44fc85b39f7d2055251d"

    fun getBaseUrl(): String {
        return if (Constant.IS_DEBUG) {
            TEST_URL
        } else {
            RELEASE_URL
        }
    }

}