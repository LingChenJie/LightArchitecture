package com.android.architecture.domain.dispatcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * File describe:
 * Author: SuQi
 * Create date: 2023/7/7
 * Modify date: 2023/7/7
 * Version: 1
 */
open class BaseViewModel : ViewModel() {

    open val TAG = javaClass.simpleName

    fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch {
            block()
        }
    }

}