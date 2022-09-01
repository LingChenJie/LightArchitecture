package com.architecture.light.data.model.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
@Entity
@Parcelize
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val uId: Long = 0,
    var username: String = "",
    var password: String = "",
    var content: String = "",
) : Parcelable