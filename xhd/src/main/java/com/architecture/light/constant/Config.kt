package com.architecture.light.constant

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
object Config {

    private const val TEST_URL = "https://test-api-open.chinaums.com/v1/fgj/front"
    private const val RELEASE_URL = "https://api-mop.chinaums.com/v1/fgj/front"

    private const val TEST_APP_ID = "10037e6f67593b5e016763785da90000"
    private const val TEST_APP_KEY = "2bf0ae27f34e44fc85b39f7d2055251d"

    private const val RELEASE_APP_ID = "8a81c1bd8264ea440182d39fe0180c34"
    private const val RELEASE_APP_KEY = "fc35355b26874aec81ff29c28a41db52"

    fun getBaseUrl(): String {
        return if (Constant.IS_DEBUG) {
            TEST_URL
        } else {
            RELEASE_URL
        }
    }

    fun getAppId(): String {
        return if (Constant.IS_DEBUG) {
            TEST_APP_ID
        } else {
            RELEASE_APP_ID
        }
    }

    fun getAppKey(): String {
        return if (Constant.IS_DEBUG) {
            TEST_APP_KEY
        } else {
            RELEASE_APP_KEY
        }
    }

}