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
    const val BACK_TO_CHOOSE_ROOM_PAGE = "Back To choose room Page"
    const val EXIT_APP = "Exit App"
    const val EXIT_LOGIN = "Exit Login"
    const val TASK_TIMEOUT = "Task Timeout"

    const val PAY_RESULT_QUERY = "Pay Query"
    const val PAY_RESULT_NOTIFY = "Pay Result Sync"
    const val PAY_RESULT_RE_SYNC = "Pay Result Re Sync"


    const val PAY_DATA_ERROR = "Pay Data Error"
    const val PRINTER_NOT_FOUND = "Printer Not Found"
    const val PRINT_EXCEPTION = "Print Exception"


    val errorCodeMap = mapOf(
        BACK_TO_PREVIOUS_PAGE to getString(R.string.error_code_back_to_previous_page),
        BACK_TO_MAIN_PAGE to getString(R.string.error_code_back_to_main_page),
        BACK_TO_CHOOSE_ROOM_PAGE to getString(R.string.error_code_back_to_choose_room_page),
        EXIT_APP to getString(R.string.error_code_exit_app),
        EXIT_LOGIN to getString(R.string.error_code_exit_login),
        TASK_TIMEOUT to getString(R.string.error_code_task_timeout),

        PAY_RESULT_QUERY to getString(R.string.error_code_pay_result_query),
        PAY_RESULT_NOTIFY to getString(R.string.error_code_pay_result_notify),

        PAY_DATA_ERROR to getString(R.string.error_code_pay_data_error),
        PRINTER_NOT_FOUND to getString(R.string.error_code_printer_not_found),
        PRINT_EXCEPTION to getString(R.string.error_code_print_exception),

        )

}