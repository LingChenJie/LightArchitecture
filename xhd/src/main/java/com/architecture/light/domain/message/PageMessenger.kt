package com.architecture.light.domain.message

import com.android.architecture.domain.dispatcher.MviDispatcher
import com.architecture.light.domain.event.Messages

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class PageMessenger : MviDispatcher<Messages>() {

    override suspend fun onHandle(event: Messages) {
        sendResult(event)
    }

}