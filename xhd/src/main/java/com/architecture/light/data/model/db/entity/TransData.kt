package com.architecture.light.data.model.db.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.architecture.light.constant.TransactionName
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.constant.TransactionStatus
import com.architecture.light.data.model.db.entity.convert.PayDataConvert
import com.architecture.light.data.model.db.entity.convert.SearchPaymentResponseConvert
import com.architecture.light.data.model.db.entity.convert.SearchReserveResponseConvert
import com.architecture.light.data.model.db.entity.convert.SearchRoomResponseConvert
import com.architecture.light.data.pay.bean.TransMemo
import com.architecture.light.data.remote.bean.SearchBillResponse
import com.architecture.light.data.remote.bean.SearchPaymentResponse
import com.architecture.light.data.remote.bean.SearchReserveResponse
import com.architecture.light.data.remote.bean.SearchRoomResponse
import java.io.Serializable

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
@TypeConverters(
    PayDataConvert::class,
    SearchRoomResponseConvert::class,
    SearchReserveResponseConvert::class,
    SearchPaymentResponseConvert::class
)
@Entity
data class TransData(
    @PrimaryKey(autoGenerate = true)
    var tId: Long = 0,
    var responseCode: String = "",
    var responseMessage: String = "",
    var transactionYear: String = "",
    var transactionTimeMillis: Long = 0,
    var transactionDate: String = "",
    var transactionTime: String = "",
    var transactionPlatform: Int = TransactionPlatform.Bank,
    var transactionName: String = TransactionName.Payment.name,
    var transactionStatus: String = TransactionStatus.TransTimeout.name,
    var transactionStatusMessage: String = "",


    var account: String = "",
    var zygwGUID: String = "",
    var projGUID: String = "",
    var roomGUID: String = "",
    var bookingGUID: String = "",
    var cardID: String? = "",
    var tel: String? = "",
    var roomInfo: String? = "",

    var totalAmount: Double = 0.0,
    var unpaidAmount: Double = 0.0,
    var amount: Double = 0.0,
    var bankAccount: String = "",
    var bankName: String = "",
    var cstName: String = "",
    var orderNumber: String = "",

    var isRePrint: Boolean = false,

    var voucherNumber: String = "",
    var serialNumber: String = "",
    var refNo: String = "",
    var vouchGUID: String = "",

    var originalVoucherNumber: String = "",
    var originalSerialNumber: String = "",
    var originalOrderNumber: String = "",


    var payData: TransMemo.PayData? = null,
    var searchRoomResponse: SearchRoomResponse? = null,
    var searchReserveResponse: SearchReserveResponse? = null,
    var searchPaymentResponse: SearchPaymentResponse? = null,
    @Ignore
    var searchBillResponse: SearchBillResponse? = null,
    @Ignore
    var userInfo: UserInfo? = null,
) : Serializable