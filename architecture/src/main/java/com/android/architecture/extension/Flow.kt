package com.android.architecture.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


fun <E> Flow<E>.flowOnLifecycleConsumeOnce(lifecycle: Lifecycle): Flow<E> = callbackFlow {
    val lastValue = LastValue(0)
    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        this@flowOnLifecycleConsumeOnce.collect {
            val newHashCode = System.identityHashCode(it)
            if (lastValue.hashCode != newHashCode) send(it)
            lastValue.hashCode = newHashCode
        }
    }
    lastValue.hashCode = 0
    close()
}

data class LastValue(var hashCode: Int)