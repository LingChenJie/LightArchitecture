package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.extension.valid
import com.android.architecture.helper.DateHelper
import com.android.architecture.helper.JsonHelper
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.data.remote.ResponseCode
import com.architecture.light.data.remote.bean.NotifyPrepaidRequest
import com.architecture.light.data.remote.bean.NotifyPrepaidResponse
import com.architecture.light.data.remote.bean.base.RequestBean
import com.architecture.light.helper.TransHelper
import com.architecture.light.settings.AccountCache
import com.architecture.light.utils.DeviceUtils

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class NotifyPrepaidTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val request = NotifyPrepaidRequest()
        request.posNO = DeviceUtils.getDeviceSN()
        request.serialNumber = param.serialNumber
        request.projGUID = param.projGUID
        request.bookingGUID = param.bookingGUID
        request.lyrCode = AccountCache.getBillRecipient()
        request.skDate = DateHelper.getDateFormatString(millis = param.transactionTimeMillis)
        request.kpr = AccountCache.getAccount()
        request.jkr = param.cstName
        if (param.transactionPlatform == TransactionPlatform.Bank) {
            var cardType = ""
            if (param.payData != null && param.payData!!.cardType.valid) {
                cardType = param.payData!!.cardType + "记卡"
            }
            request.payMode =
                TransHelper.getTransactionPlatform(param.transactionPlatform) + cardType
        } else {
            request.payMode = TransHelper.getTransactionPlatform(param.transactionPlatform)
        }
        val searchPaymentResponse = param.searchPaymentResponse!!
        val getinList = mutableListOf<NotifyPrepaidRequest.Getin>()
        val getin: NotifyPrepaidRequest.Getin
        for (payment in searchPaymentResponse.data!!) {
            if (payment.isSubLevel && payment.isChecked) {
                getin = NotifyPrepaidRequest.Getin()
                getin.itemNameGUID = payment.feeItemGUID
                getin.itemName = payment.feeItemName
                getin.amount = param.amount.toString()
                getin.posCode = param.refNo
                getin.rzBank = param.bankName
                getinList.add(getin)
                break
            }
        }
        request.getinList = getinList
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = JsonHelper.toBean<NotifyPrepaidResponse>(responseStr)
        if (response.code == ResponseCode.SUCCESS) {
            param.responseMessage = ErrorCode.getMessage(AppErrorCode.TRANS_NOTIFY_SUCCESS)
            param.vouchGUID = response.data.vouchGUID
        } else {
            param.responseCode = response.code
            param.responseMessage = response.msg
        }
    }

}