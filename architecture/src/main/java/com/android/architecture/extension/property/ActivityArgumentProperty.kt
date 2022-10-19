package com.android.architecture.extension.property

import android.app.Activity
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
class ActivityArgumentProperty<T>(
    private val defaultValue: T? = null
) : ReadOnlyProperty<Activity, T> {

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        return thisRef.intent?.extras?.getValue(property.name) as? T
            ?: defaultValue
            ?: throw IllegalStateException("Property ${property.name} could not be read")
    }

}