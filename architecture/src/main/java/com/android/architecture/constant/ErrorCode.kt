package com.android.architecture.constant

import com.android.architecture.R
import com.android.architecture.extension.getString
import com.android.architecture.extension.valid


object ErrorCode {

    const val SUCCESS = "Success"
    const val USER_CANCEL = "User Cancel"

    const val NETWORK_NO_CONNECTION = "Network No Connection"
    const val NETWORK_TIMEOUT = "Network Timeout"
    const val NETWORK_ERROR = "Network Error"
    const val NETWORK_DATA_ERROR = "Network Data Error"

    const val DATA_EMPTY = "Data Empty"


    private val errorCodeMap = mutableMapOf(
        SUCCESS to getString(R.string.error_code_success),
        USER_CANCEL to getString(R.string.error_code_user_cancel),

        NETWORK_NO_CONNECTION to getString(R.string.error_code_network_no_connection),
        NETWORK_TIMEOUT to getString(R.string.error_code_network_timeout),
        NETWORK_ERROR to getString(R.string.error_code_network_error),
        NETWORK_DATA_ERROR to getString(R.string.error_code_network_data_error),

        DATA_EMPTY to getString(R.string.error_code_data_empty),

        )

    fun add(map: Map<String, String>) {
        errorCodeMap.putAll(map)
    }

    fun getMessage(code: String, message: String? = null): String {
        if (message.valid) {
            return message!!
        }
        return errorCodeMap[code] ?: getString(R.string.error_code_undefined)
    }

}