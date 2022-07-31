package com.architecture.sample.domain.request

import com.android.architecture.domain.dispatcher.MviDispatcher
import com.architecture.sample.domain.event.ComplexEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
class ComplexRequester : MviDispatcher<ComplexEvent>() {

    override suspend fun onHandle(event: ComplexEvent) {
        when (event) {
            is ComplexEvent.ResultTest1 -> {
                interval(1000).collect {
                    input(ComplexEvent.ResultTest4(it))
                }
            }
            is ComplexEvent.ResultTest2 -> {
                timer(1000).collect {
                    sendResult(event)
                }
            }
            is ComplexEvent.ResultTest3 -> {
                sendResult(event)
            }
            is ComplexEvent.ResultTest4 -> {
                sendResult(event)
            }
        }
    }

    private fun interval(duration: Long) = flow {
        for (i in 0..Int.MAX_VALUE) {
            delay(duration)
            emit(i)
        }
    }

    private fun timer(duration: Long) = flow {
        delay(duration)
        emit(true)
    }

}