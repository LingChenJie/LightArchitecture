package com.architecture.light.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.architecture.light.data.local.db.dao.NoteDao
import com.architecture.light.data.local.db.entity.Note

@Database(entities = [Note::class], version = 1, exportSchema = true)
abstract class AppDB : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}