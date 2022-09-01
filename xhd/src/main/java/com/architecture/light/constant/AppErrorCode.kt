package com.architecture.light.constant

import com.android.architecture.extension.getString
import com.architecture.light.R

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
object AppErrorCode {

    const val EXIT_APP = "Exit App"
    const val TASK_TIMEOUT = "Task Timeout"


    val errorCodeMap = mapOf(
        EXIT_APP to getString(R.string.error_code_exit_app),
        TASK_TIMEOUT to getString(R.string.error_code_task_timeout),

        )

}