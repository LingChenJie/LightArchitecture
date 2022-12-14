package com.architecture.light.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.architecture.light.data.local.db.dao.NoteDao
import com.architecture.light.data.local.db.entity.Note

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
@Database(entities = [Note::class], version = 1, exportSchema = true)
abstract class AppDB : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}