package com.android.architecture.domain.dispatcher

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.android.architecture.helper.Logger
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

/**
 * Create by KunMinX at 2022/7/3
 */
abstract class MviDispatcher<E> : ViewModel() {
    companion object {
        private const val DEFAULT_QUEUE_LENGTH = 10
        private const val START_VERSION = -1
    }

    protected val TAG = this.javaClass.simpleName
    private val _sharedFlow: MutableSharedFlow<E> by lazy {
        MutableSharedFlow(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = initQueueMaxLength()
        )
    }
    private val delayMap: MutableMap<Int, Boolean> = mutableMapOf()
    private val lastValue = LastValue(0)
    private val currentVersion = AtomicInteger(START_VERSION)


    protected open fun initQueueMaxLength(): Int {
        return DEFAULT_QUEUE_LENGTH
    }

    fun output(activity: AppCompatActivity, observer: (E) -> Unit) {
        delayMap[System.identityHashCode(activity)] = true
        val observerWrapper = ObserverWrapper(observer, currentVersion.get())
        activity.lifecycleScope.launch {
            activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delayMap.remove(System.identityHashCode(activity))
//                _sharedFlow.flowConsumeOnce().collect {
//                    Logger.i(TAG, "----collect: $activity")
//                    observer.invoke(it)
//                }
                _sharedFlow.collect {
                    Logger.i(TAG, "----collect: $activity")
                    //observer.invoke(it)
                    observerWrapper.onChanged(it)
                }
            }
        }
    }

    fun output(fragment: Fragment, observer: (E) -> Unit) {
        delayMap[System.identityHashCode(fragment)] = true
        val observerWrapper = ObserverWrapper(observer, currentVersion.get())
        fragment.viewLifecycleOwner.lifecycleScope.launch {
            fragment.viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delayMap.remove(System.identityHashCode(fragment))
//                _sharedFlow.flowConsumeOnce().collect {
//                    Logger.i(TAG, "----collect: $fragment")
//                    observer.invoke(it)
//                }
                _sharedFlow.collect {
                    Logger.i(TAG, "----collect: $fragment")
                    //observer.invoke(it)
                    observerWrapper.onChanged(it)
                }
            }
        }
    }

    protected suspend fun sendResult(event: E) {
        _sharedFlow.emit(event)
    }

    fun input(event: E) {
        currentVersion.getAndIncrement()
        viewModelScope.launch {
            if (needDelayForLifecycleState) delayForLifecycleState().collect { onHandle(event) }
            else onHandle(event)
        }
    }

    protected abstract suspend fun onHandle(event: E)

    private val needDelayForLifecycleState
        get() = delayMap.isNotEmpty()

    private fun delayForLifecycleState() = flow {
        delay(1)
        emit(true)
    }

    inner class ObserverWrapper<T>(private val observer: (T) -> Unit, val version: Int) {
        fun onChanged(t: T) {
            if (currentVersion.get() > version) {
                observer.invoke(t)
            }
        }
    }

    private fun <E> Flow<E>.flowConsumeOnce(): Flow<E> = callbackFlow {
        this@flowConsumeOnce.collect {
            val newHashCode = System.identityHashCode(it)
            if (lastValue.hashCode != newHashCode) send(it)
            lastValue.hashCode = newHashCode
        }
        lastValue.hashCode = 0
        close()
    }

    data class LastValue(var hashCode: Int)

}