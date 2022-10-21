package com.android.architecture.helper

/**
 * File describe:异步任务处理
 * Author: SuQi
 * Create date: 2022/10/21
 * Modify date: 2022/10/21
 * Version: 1
 */
object AsyncTask {

    fun <T> execute(start: () -> T, end: ((result: T) -> Unit)? = null) {
        AppExecutors.getInstance().io().execute {
            val result = start.invoke()
            AppExecutors.getInstance().main().execute {
                end?.invoke(result)
            }
        }
    }

}