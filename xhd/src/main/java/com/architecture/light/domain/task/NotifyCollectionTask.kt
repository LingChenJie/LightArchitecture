package com.architecture.light.domain.task

import com.android.architecture.constant.ErrorCode
import com.android.architecture.extension.valid
import com.android.architecture.helper.DateHelper
import com.android.architecture.helper.JsonHelper
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.data.remote.ResponseCode
import com.architecture.light.data.remote.bean.NotifyCollectionRequest
import com.architecture.light.data.remote.bean.NotifyCollectionResponse
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.data.remote.bean.base.RequestBean
import com.architecture.light.helper.TransHelper
import com.architecture.light.settings.AccountCache
import com.architecture.light.utils.DeviceUtils

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class NotifyCollectionTask : HttpTask() {

    override fun onAssembly(): RequestBean {
        val request = NotifyCollectionRequest()
        request.posNO = DeviceUtils.getDeviceSN()
        request.serialNumber = param.serialNumber
        request.projGUID = param.projGUID
        request.roomGUID = param.roomGUID
        request.lyrCode = AccountCache.getBillRecipient()
        request.skDate = DateHelper.getDateFormatString(millis = param.transactionTimeMillis)
        request.kpr = AccountCache.getUsername()
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
        val roomResponse = param.searchRoomResponse!!
        var selectRoom: SearchRoomResponse.Data? = null
        for (room in roomResponse.data) {
            if (room.isChecked) {
                selectRoom = room
                break
            }
        }
        val feeList = selectRoom!!.feeList
        val getinList = mutableListOf<NotifyCollectionRequest.Getin>()
        var getin: NotifyCollectionRequest.Getin
        for (fee in feeList) {
            if (fee.isChecked) {
                getin = NotifyCollectionRequest.Getin()
                getin.itemNameGUID = fee.itemNameGUID
                getin.itemName = fee.itemName
                getin.amount = fee.paymentAmount.toString()
                getin.feeGUID = fee.feeGUID
                getin.posCode = param.refNo
                getin.rzBank = param.bankName
                getinList.add(getin)
            }
        }
        request.getinList = getinList
        return request
    }

    override fun onPostExecute(responseStr: String) {
        val response = JsonHelper.toBean<NotifyCollectionResponse>(responseStr)
        if (response.code == ResponseCode.SUCCESS) {
            param.responseMessage = ErrorCode.getMessage(AppErrorCode.TRANS_NOTIFY_SUCCESS)
            param.vouchGUID = response.data.vouchGUID
        } else {
            param.responseCode = response.code
            param.responseMessage = response.msg
        }
    }

}