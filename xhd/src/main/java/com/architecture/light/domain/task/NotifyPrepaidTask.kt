package com.architecture.light.domain.task

import com.android.architecture.helper.JsonHelper
import com.architecture.light.data.remote.bean.NotifyCollectionResponse
import com.architecture.light.data.remote.bean.NotifyPrepaidRequest
import com.architecture.light.data.remote.bean.base.RequestBean
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
        request.serialNumber = param.voucherNumber
        request.projGUID = param.projGUID
        request.lyrCode = AccountCache.getBillRecipient()
        request.skDate = param.transactionDate
        request.kpr = AccountCache.getAccount()
        request.jkr = param.cstName
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