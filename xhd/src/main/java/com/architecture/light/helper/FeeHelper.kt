package com.architecture.light.helper

import com.android.architecture.utils.DoubleUtils

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/18
 * Modify date: 2022/11/18
 * Version: 1
 */
object FeeHelper {

    // 微信支付宝都是0.25%乘以交易额，
    // 银行卡贷记卡是0.53%乘以交易额
    // 银行卡借记卡0.45%乘以交易额 最大是16.75元
    fun calcFee(payType: String, amount: Double): Double {
        var fee = 0.0
        when (payType) {
            "微信", "支付宝" -> {
                fee = DoubleUtils.halfUpTwoDecimalPlaces(
                    DoubleUtils.multiply(
                        amount,
                        0.0025
                    )
                )
            }
            "银行卡借记卡" -> {
                var result =
                    DoubleUtils.multiply(
                        amount,
                        0.0045
                    )
                if (result > 16.75) {
                    result = 16.75
                }
                fee = result
            }
            "银行卡贷记卡" -> {
                fee = DoubleUtils.halfUpTwoDecimalPlaces(
                    DoubleUtils.multiply(
                        amount,
                        0.0053
                    )
                )
            }
        }
        return fee
    }

}