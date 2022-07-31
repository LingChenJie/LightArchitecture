package com.architecture.sample.data.model.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val nId: Long,
    val title: String,
    val content: String,
    @ColumnInfo(name = "create_time")
    val creteTime: Long,
    @ColumnInfo(name = "modify_time")
    val modifyTime: Long,
    val type: Int,
) : Parcelable {

    companion object {
        const val TYPE_TOPPING = 0x0001
        const val TYPE_MARKED = 0x0002
    }

    @Ignore
    constructor() : this(0, "", "", 0, 0, 0)
}