package com.android.architecture.domain.dispatcher

import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.android.architecture.helper.Logger
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

/**
 * Create by KunMinX at 2022/7/3
 */
abstract class MviDispatcher<E> : ViewModel() {
    companion object {
        private const val DEFAULT_QUEUE_LENGTH = 1
        private const val START_VERSION = -1
    }

    protected val TAG = this.javaClass.simpleName
    private val _sharedFlow: MutableSharedFlow<E> by lazy {
        MutableSharedFlow(
            replay = initQueueMaxLength(),
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = initQueueMaxLength()
        )
    }
    private val delayArray: SparseArray<Boolean> = SparseArray()
    private val currentVersion = AtomicInteger(START_VERSION)

    protected open fun initQueueMaxLength(): Int {
        return DEFAULT_QUEUE_LENGTH
    }

    fun output(activity: AppCompatActivity, observer: (E) -> Unit) {
        delayArray[System.identityHashCode(activity)] = true
        val observerWrapper = ObserverWrapper(observer, currentVersion.get())
        activity.lifecycleScope.launch {
            activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delayArray.remove(System.identityHashCode(activity))
                _sharedFlow.collect {
                    Logger.i(TAG, "----collect: $activity")
                    observerWrapper.onChanged(it)
                }
            }
        }
    }

    fun output(fragment: Fragment, observer: (E) -> Unit) {
        delayArray[System.identityHashCode(fragment)] = true
        val observerWrapper = ObserverWrapper(observer, currentVersion.get())
        fragment.viewLifecycleOwner.lifecycleScope.launch {
            fragment.viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delayArray.remove(System.identityHashCode(fragment))
                _sharedFlow.collect {
                    Logger.i(TAG, "----collect: $fragment")
                    observerWrapper.onChanged(it)
                }
            }
        }
    }

    protected suspend fun sendResult(event: E) {
        currentVersion.getAndIncrement()
        _sharedFlow.emit(event)
    }

    fun input(event: E) {
        viewModelScope.launch {
            if (needDelayForLifecycleState) delayForLifecycleState().collect { onHandle(event) }
            else onHandle(event)
        }
    }

    protected abstract suspend fun onHandle(event: E)

    private val needDelayForLifecycleState
        get() = delayArray.isNotEmpty()

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

}