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

    const val BACK_TO_PREVIOUS_PAGE = "Back To Previous Page"
    const val BACK_TO_MAIN_PAGE = "Back To Main Page"
    const val EXIT_APP = "Exit App"
    const val EXIT_LOGIN = "Exit Login"
    const val TASK_TIMEOUT = "Task Timeout"


    val errorCodeMap = mapOf(
        BACK_TO_PREVIOUS_PAGE to getString(R.string.error_code_back_to_previous_page),
        BACK_TO_MAIN_PAGE to getString(R.string.error_code_back_to_main_page),
        EXIT_APP to getString(R.string.error_code_exit_app),
        EXIT_LOGIN to getString(R.string.error_code_exit_login),
        TASK_TIMEOUT to getString(R.string.error_code_task_timeout),

        )

}