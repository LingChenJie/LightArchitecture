package com.architecture.light.constant

import com.android.architecture.extension.getString
import com.android.architecture.extension.valid
import com.architecture.light.R


object PrintErrorCode {

    const val CODE_0 = "0"
    const val CODE_1 = "-1"
    const val CODE_2 = "-2"
    const val CODE_3 = "-3"
    const val CODE_4 = "-4"
    const val CODE_101 = "-101"
    const val CODE_102 = "-102"
    const val CODE_103 = "-103"


    private val errorCodeMap = mapOf(
        CODE_0 to getString(R.string.error_code_print_0),
        CODE_1 to getString(R.string.error_code_print_1),
        CODE_2 to getString(R.string.error_code_print_2),
        CODE_3 to getString(R.string.error_code_print_3),
        CODE_4 to getString(R.string.error_code_print_4),
        CODE_101 to getString(R.string.error_code_print_101),
        CODE_102 to getString(R.string.error_code_print_102),
        CODE_103 to getString(R.string.error_code_print_103),

        )

    fun getMessage(code: String, message: String? = null): String {
        if (message.valid) {
            return message!!
        }
        return errorCodeMap[code] ?: getString(R.string.error_code_print_unknown)
    }

}