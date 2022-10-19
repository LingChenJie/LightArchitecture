package com.android.architecture.extension.property

import androidx.fragment.app.Fragment
import com.android.architecture.extension.getValue
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/19
 * Modify date: 2022/10/19
 * Version: 1
 */
class FragmentArgumentProperty<T>(
    private val defaultValue: T? = null
) : ReadOnlyProperty<Fragment, T> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return thisRef.arguments?.getValue(property.name) as? T
            ?: defaultValue
            ?: throw IllegalStateException("Property ${property.name} could not be read")
    }

}