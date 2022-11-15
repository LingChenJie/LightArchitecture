package com.architecture.light.domain.message

import com.android.architecture.domain.dispatcher.MviDispatcher
import com.architecture.light.domain.event.CommonMessages

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/15
 * Modify date: 2022/11/15
 * Version: 1
 */
class CommonMessenger : MviDispatcher<CommonMessages>() {

    override suspend fun onHandle(event: CommonMessages) {
        sendResult(event)
    }

}