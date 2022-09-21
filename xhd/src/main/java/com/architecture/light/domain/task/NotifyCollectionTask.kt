package com.architecture.light.domain.task

import com.android.architecture.helper.JsonHelper
import com.architecture.light.data.remote.bean.NotifyCollectionRequest
import com.architecture.light.data.remote.bean.NotifyCollectionResponse
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.data.remote.bean.base.RequestBean
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
        request.serialNumber = param.voucherNumber
        request.zygwGUID = param.zygwGUID
        request.projGUID = param.projGUID
        request.roomGUID = param.roomGUID
        request.lyrCode = AccountCache.getBillRecipient()
        request.skDate = param.transactionDate
        request.kpr = AccountCache.getAccount()
        request.jkr = param.cstName
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
                getin.amount = fee.paymentAmount
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
//        if (response.code == ResponseCode.SUCCESS) {
//            if (response.data != null && response.data.size > 0) {
//                param.responseCode = ErrorCode.SUCCESS
//                param.responseMessage = response.msg
//                param.searchRoomResponse = response
//            } else {
//                param.responseCode = ErrorCode.DATA_EMPTY
//                param.responseMessage = ErrorCode.getMessage(param.responseCode)
//            }
//        } else {
//            param.responseCode = response.code
//            param.responseMessage = response.msg
//        }
    }

}