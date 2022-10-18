package com.android.architecture.helper

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/18
 * Modify date: 2022/10/18
 * Version: 1
 */
class LifecycleAwareViewBinding<F : Fragment, V : ViewBinding>(
    private val bindingCreator: (F) -> V
) : ReadOnlyProperty<F, V>, LifecycleEventObserver {

    private var binding: V? = null

    override fun getValue(thisRef: F, property: KProperty<*>): V {
        binding?.let {
            return it
        }
        val lifecycle = thisRef.viewLifecycleOwner.lifecycle
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            this.binding = null
            throw IllegalStateException("Can't access ViewBinding after onDestroyView")
        } else {
            lifecycle.addObserver(this)
            val viewBinding = bindingCreator.invoke(thisRef)
            this.binding = viewBinding
            return viewBinding
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            binding = null
            source.lifecycle.removeObserver(this)
        }
    }

}