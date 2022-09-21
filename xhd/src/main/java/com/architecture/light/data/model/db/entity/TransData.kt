package com.architecture.light.data.model.db.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.data.pay.bean.TransMemo
import com.architecture.light.data.remote.bean.SearchBillResponse
import com.architecture.light.data.remote.bean.SearchReserveResponse
import com.architecture.light.data.remote.bean.SearchRoomResponse
import java.io.Serializable

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
@Entity
data class TransData(
    @PrimaryKey(autoGenerate = true)
    val tId: Long = 0,
    var responseCode: String = "",
    var responseMessage: String = "",
    var transactionYear: String = "",
    var transactionDate: String = "",
    var transactionTime: String = "",
    var transactionPlatform: Int = TransactionPlatform.Bank,


    var account: String = "",
    var zygwGUID: String = "",
    var projGUID: String = "",
    var roomGUID: String = "",
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
    var refNo: String = "",

    var originalVoucherNumber: String = "",
    var originalOrderNumber: String = "",

    var payDataStr: String = "",

    @Ignore
    var payData: TransMemo.PayData? = null,
    @Ignore
    var searchRoomResponse: SearchRoomResponse? = null,
    @Ignore
    var searchReserveResponse: SearchReserveResponse? = null,
    @Ignore
    var searchBillResponse: SearchBillResponse? = null,
    @Ignore
    var userInfo: UserInfo? = null,
) : Serializable