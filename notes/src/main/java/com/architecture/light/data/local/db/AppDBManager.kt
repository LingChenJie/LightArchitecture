package com.architecture.light.data.local.db

import androidx.room.Room
import com.android.architecture.utils.AppUtils

class AppDBManager private constructor() {

    companion object {
        private const val DATABASE_NAME = "notes.db"

        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            AppDBManager()
        }
    }

    private val database: AppDB = Room.databaseBuilder(
        AppUtils.getApp(),
        AppDB::class.java,
        DATABASE_NAME
    ).build()

    fun getDataBase(): AppDB {
        return database
    }

}