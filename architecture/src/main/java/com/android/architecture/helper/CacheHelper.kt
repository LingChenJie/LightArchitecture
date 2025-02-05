package com.android.architecture.helper

import com.android.architecture.utils.AppUtils
import com.tencent.mmkv.MMKV

object CacheHelper {

    private val instance by lazy {
        MMKV.initialize(AppUtils.getApp())
        MMKV.defaultMMKV()
    }

    fun saveString(key: String, value: String? = null): Boolean {
        return if (value == null) {
            instance.encode(key, "")
        } else {
            instance.encode(key, value)
        }
    }

    fun getString(key: String): String {
        return instance.decodeString(key, "") ?: ""
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return instance.decodeString(key, defaultValue) ?: defaultValue
    }

    fun saveBool(key: String, flag: Boolean) {
        instance.encode(key, flag)
    }

    fun getBool(key: String): Boolean {
        return instance.decodeBool(key, false)
    }

    fun getBool(key: String, defaultValue: Boolean = false): Boolean {
        return instance.decodeBool(key, defaultValue)
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

    fun getInt(key: String): Int {
        return instance.decodeInt(key, 0)
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