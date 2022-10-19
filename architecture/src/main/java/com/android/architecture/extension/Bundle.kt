package com.android.architecture.extension

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/19
 * Modify date: 2022/10/19
 * Version: 1
 */
operator fun <T> Bundle.set(key: String, value: T?) {
    when (value) {
        is Boolean -> putBoolean(key, value)
        is Byte -> putByte(key, value)
        is Char -> putChar(key, value)
        is Short -> putShort(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        is Double -> putDouble(key, value)
        is String? -> putString(key, value)
        is CharSequence? -> putCharSequence(key, value)
        is Serializable? -> putSerializable(key, value) // also ArrayList
        is Parcelable? -> putParcelable(key, value)
        is Bundle? -> putBundle(key, value)
        is BooleanArray? -> putBooleanArray(key, value)
        is ByteArray? -> putByteArray(key, value)
        is CharArray? -> putCharArray(key, value)
        is ShortArray? -> putShortArray(key, value)
        is IntArray? -> putIntArray(key, value)
        is LongArray? -> putLongArray(key, value)
        is FloatArray? -> putFloatArray(key, value)
        is DoubleArray? -> putDoubleArray(key, value)
        is ArrayList<*>? -> throw IllegalStateException("ArrayList<*> $key is not supported")
        is Array<*>? -> throw IllegalStateException("Array<*> $key is not supported")
        else -> throw IllegalStateException("Type $key is not supported")
    }
}

fun <T> Bundle.getValue(key: String): T? {
    @Suppress("UNCHECKED_CAST")
    return get(key) as T?
}

operator fun <T> Intent.set(key: String, value: T) {
    when (value) {
        is Boolean -> putExtra(key, value)
        is Byte -> putExtra(key, value)
        is Char -> putExtra(key, value)
        is Short -> putExtra(key, value)
        is Int -> putExtra(key, value)
        is Long -> putExtra(key, value)
        is Float -> putExtra(key, value)
        is Double -> putExtra(key, value)
        is String? -> putExtra(key, value)
        is CharSequence? -> putExtra(key, value)
        is Serializable? -> putExtra(key, value)
        is Parcelable? -> putExtra(key, value)
        is Bundle? -> putExtra(key, value)
        is BooleanArray? -> putExtra(key, value)
        is ByteArray? -> putExtra(key, value)
        is CharArray? -> putExtra(key, value)
        is ShortArray? -> putExtra(key, value)
        is IntArray? -> putExtra(key, value)
        is LongArray? -> putExtra(key, value)
        is FloatArray? -> putExtra(key, value)
        is DoubleArray? -> putExtra(key, value)
        is ArrayList<*>? -> throw IllegalStateException("ArrayList<*> $key is not supported")
        is Array<*>? -> throw IllegalStateException("Array<*> $key is not supported")
        else -> throw IllegalStateException("Type $key is not supported")
    }
}