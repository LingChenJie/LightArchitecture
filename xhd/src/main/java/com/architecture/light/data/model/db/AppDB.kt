package com.architecture.light.data.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.architecture.light.data.model.db.dao.NoteDao
import com.architecture.light.data.model.db.dao.UserInfoDao
import com.architecture.light.data.model.db.entity.Note
import com.architecture.light.data.model.db.entity.UserInfo

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
@Database(entities = [Note::class, UserInfo::class], version = 1, exportSchema = true)
abstract class AppDB : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun userInfoDao(): UserInfoDao
}