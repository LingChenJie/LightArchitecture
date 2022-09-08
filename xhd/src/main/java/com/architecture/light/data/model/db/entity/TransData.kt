package com.architecture.light.data.model.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.data.remote.bean.SearchRoomResponse
import kotlinx.parcelize.Parcelize

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
@Entity
@Parcelize
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
    var cardID: String = "",
    var tel: String = "",
    var roomInfo: String = "",

    var amount: Long = 0,
    var orderNumber: String = "",
    var bankAccount: String = "",
    var bankName: String = "",

    var originalVoucherNumber: String = "",
    var originalOrderNumber: String = "",

    @Ignore
    var searchRoomResponse: SearchRoomResponse? = null,
    @Ignore
    var userInfo: UserInfo? = null,
) : Parcelable