package com.architecture.light.data.model.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
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

    var zygwGUID: String = "",
    var projGUID: String = "",
    var cardID: String = "",
    var tel: String = "",
    var roomInfo: String = "",


    @Ignore
    var userInfo: UserInfo? = null,
) : Parcelable