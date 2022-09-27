package com.architecture.light.helper

import com.android.architecture.extension.getString
import com.architecture.light.R
import com.architecture.light.constant.TransactionName
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.constant.TransactionStatus
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

    fun getTransactionName(transData: TransData): String {
        var result = ""
        when (transData.transactionName) {
            TransactionName.Payment.name -> {
                result = getString(R.string.main_payment)
            }
            TransactionName.Reserve.name -> {
                result = getString(R.string.main_pledge_money)
            }
            TransactionName.Void.name -> {
                result = getString(R.string.main_void)
            }
        }
        return result
    }

    fun getPaymentStatus(transData: TransData): String {
        var result = ""
        when (transData.transactionStatus) {
            TransactionStatus.TransSucceed.name -> {
                result = getString(R.string.payment_result_pay_success)
            }
            TransactionStatus.TransFailed.name -> {
                result = getString(R.string.payment_result_pay_fail)
            }
            TransactionStatus.TransTimeout.name -> {
                result = getString(R.string.payment_result_pay_timeout)
            }
            TransactionStatus.ResultNotifySucceed.name -> {
                result = getString(R.string.payment_result_sync_success)
            }
            TransactionStatus.ResultNotifyFailed.name -> {
                result = getString(R.string.payment_result_sync_fail)
            }
        }
        return result
    }

    fun getVoidStatus(transData: TransData): String {
        var result = ""
        when (transData.transactionStatus) {
            TransactionStatus.TransSucceed.name -> {
                result = getString(R.string.void_result_success)
            }
            TransactionStatus.TransFailed.name -> {
                result = getString(R.string.void_result_fail)
            }
            TransactionStatus.TransTimeout.name -> {
                result = getString(R.string.void_result_timeout)
            }
            TransactionStatus.ResultNotifySucceed.name -> {
                result = getString(R.string.void_result_sync_success)
            }
            TransactionStatus.ResultNotifyFailed.name -> {
                result = getString(R.string.void_result_sync_fail)
            }
        }
        return result
    }

    fun getPaymentSyncIsSuccess(transData: TransData): Boolean {
        val status = transData.transactionStatus
        if (status == TransactionStatus.ResultNotifySucceed.name
            || status == TransactionStatus.GetPrintDataSucceed.name
            || status == TransactionStatus.GetPrintDataFailed.name
            || status == TransactionStatus.PrintSucceed.name
            || status == TransactionStatus.PrintFailed.name
        ) {
            return true
        }
        return false
    }

}