package com.architecture.light.utils

import com.android.architecture.helper.AmountHelper

/**
 * Created by SuQi on 2022/8/3.
 * Describe:
 */
fun main(args: Array<String>) {
    println(AmountHelper.convertAmount("1,010,000.18"))
    val stringCent2Long = AmountHelper.formatAmount(AmountHelper.convertAmount("0.18"))
    println(stringCent2Long)
//    val yuan2Fen12 = AmountHelper.yuan2Fen12(-1.100001)
//    println(yuan2Fen12)
}
