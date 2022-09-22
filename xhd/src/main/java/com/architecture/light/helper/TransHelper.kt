package com.architecture.light.helper

import com.android.architecture.extension.getString
import com.architecture.light.R
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.utils.DeviceUtils

/**
 * File describe:
 * Author: SuQi
 * Create date: 9/19/22
 * Modify date: 9/19/22
 * Version: 1
 */
object TransHelper {

    fun getTransactionPlatform(transactionPlatform: Int): String {
        return when (transactionPlatform) {
            TransactionPlatform.Bank -> getString(R.string.payment_bankcard)
            TransactionPlatform.AliPay -> getString(R.string.payment_alipay)
            TransactionPlatform.WechatPay -> getString(R.string.payment_wechat)
            else -> ""
        }
    }

    fun getTransactionSerialNumber(transData: TransData): String {
        val sn = DeviceUtils.getDeviceSN()
        val date = transData.transactionDate
        val time = transData.transactionTime
        return sn + date + time + transData.voucherNumber
    }

}