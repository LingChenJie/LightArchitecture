package com.architecture.light.data.local.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

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
        const val TYPE_NORMAL = 0x0000
        const val TYPE_TOPPING = 0x0001
        const val TYPE_MARKED = 0x0002
        const val TYPE_LOCKED = 0x0004
    }

    @Ignore
    constructor() : this(0, "", "", 0, 0, TYPE_NORMAL)

    @IgnoredOnParcel
    @Ignore
    var mutableType: Int = type

    @Ignore
    fun toggleType(param: Int) {
        mutableType = if (mutableType and param != 0) {
            mutableType and param.inv()
        } else {
            mutableType or param
        }
    }

    @get:Ignore
    val isMarked: Boolean
        get() = mutableType and TYPE_MARKED != 0

    @get:Ignore
    val isTopping: Boolean
        get() = mutableType and TYPE_TOPPING != 0

    @get:Ignore
    val isLocked: Boolean
        get() = mutableType and TYPE_LOCKED != 0

    fun copy(): Note {
        return Note(nId, title, content, creteTime, modifyTime, mutableType)
    }

}