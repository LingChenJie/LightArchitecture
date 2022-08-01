package com.android.architecture.helper

import com.tencent.mmkv.MMKV

object CacheHelper {

    private val instance by lazy { MMKV.defaultMMKV() }

    fun saveString(key: String, value: String? = null): Boolean {
        return if (value == null) {
            instance.encode(key, "")
        } else {
            instance.encode(key, value)
        }
    }

    fun getString(key: String): String {
        return instance.decodeString(key) ?: ""
    }

    fun saveFlag(key: String, flag: Boolean) {
        instance.encode(key, flag)
    }

    fun getFlag(key: String, defaultValue: Boolean = false): Boolean {
        return instance.decodeBool(key, defaultValue)
    }

    fun saveInt(key: String, value: Int) {
        instance.encode(key, value)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return instance.decodeInt(key, defaultValue)
    }

    fun saveFloat(key: String, float: Float) {
        instance.encode(key, float)
    }

    fun getFloat(key: String, defaultValue: Float = 0.0f): Float {
        return instance.decodeFloat(key, defaultValue)
    }

    fun saveDouble(key: String, double: Double) {
        instance.encode(key, double)
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return instance.decodeDouble(key, defaultValue)
    }

}