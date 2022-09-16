package com.architecture.light.utils

import com.architecture.light.helper.AmountHelper

/**
 * Created by SuQi on 2022/8/3.
 * Describe:
 */
fun main(args: Array<String>) {
    val stringCent2Long = AmountHelper.convertAmount("10,001")
    println(stringCent2Long)
}
