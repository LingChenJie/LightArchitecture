package com.android.architecture.helper

/**
 * Created by SuQi on 2022/9/26.
 * Describe:
 */
object CloneHelper {

    inline fun <reified T> clone(any: Any): T {
        val json = JsonHelper.toJson(any)
        return JsonHelper.toBean(json)
    }

}