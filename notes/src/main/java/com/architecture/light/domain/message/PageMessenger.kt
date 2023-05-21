package com.architecture.light.domain.message

import com.android.architecture.domain.dispatcher.MviDispatcher
import com.architecture.light.domain.event.MviMessages

class PageMessenger : MviDispatcher<MviMessages>() {

    override suspend fun onHandle(event: MviMessages) {
        sendResult(event)
    }

}