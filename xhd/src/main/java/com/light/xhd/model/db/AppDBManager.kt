package com.architecture.sample.data.model.db

import androidx.room.Room
import com.android.architecture.utils.AppUtils

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
class AppDBManager private constructor() {

    companion object {
        private const val DATABASE_NAME = "LIGHT_ARCHITECTURE.db"

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