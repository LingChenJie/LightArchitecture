package com.architecture.light.utils

import com.architecture.light.helper.FeeHelper

/**
 * Created by SuQi on 2022/8/3.
 * Describe:
 */
fun main(args: Array<String>) {
//    println(AmountHelper.convertAmount("1,010,000.18"))
//    val stringCent2Long = AmountHelper.formatAmount(AmountHelper.convertAmount("0.18"))
//    println(stringCent2Long)
//    val yuan2Fen12 = AmountHelper.yuan2Fen12(-1.100001)
//    println(yuan2Fen12)
//    val result = DoubleUtils.halfUpTwoDecimalPlaces(10.004555)
//    println(result)
//    val result = DoubleUtils.multiply(3723.0, 0.0045)
//    println(result)
    var calcFee = FeeHelper.calcFee("支付宝", 0.0)
    println(calcFee)
    calcFee = FeeHelper.calcFee("银行卡贷记卡", 0.000000)
    println(calcFee)
    calcFee = FeeHelper.calcFee("银行卡借记卡", 2290.220000)
    println(calcFee)
}
