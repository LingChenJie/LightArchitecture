package com.android.architecture.helper

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

/**
 * Created by SuQi on 2022/9/5.
 * Describe:
 */
object JsonHelper {
    val gson = Gson()

    fun toJson(any: Any): String {
        return gson.toJson(any)
    }

    fun <T> toBean(json: String, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

    fun <T> toList(json: String, classOfT: Class<T>): List<T> {
        val list = ArrayList<T>()
        val array = JsonParser.parseString(json).asJsonArray
        for (elem in array) {
            list.add(gson.fromJson(elem, classOfT))
        }
        return list
    }

    inline fun <reified T> toBean(json: String): T {
        return gson.fromJson(json, T::class.java)
    }

    inline fun <reified T> toList(json: String): List<T> {
        return gson.fromJson(json, object : TypeToken<List<T>>() {}.type)
    }

    inline fun <reified T> toListMap(json: String): List<Map<String, T>> {
        return gson.fromJson(json, object : TypeToken<List<Map<String, T>>>() {}.type)
    }

    inline fun <reified T> toMap(json: String): Map<String, T> {
        return gson.fromJson(json, object : TypeToken<Map<String, T>>() {}.type)
    }

}