package com.android.architecture.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.android.architecture.helper.LifecycleAwareViewBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/18
 * Modify date: 2022/10/18
 * Version: 1
 */
inline fun <reified V : ViewBinding> Fragment.binding(): LifecycleAwareViewBinding<Fragment, V> {
    val method = V::class.java.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )
    return LifecycleAwareViewBinding { method.invoke(null, layoutInflater, null, false) as V }
}