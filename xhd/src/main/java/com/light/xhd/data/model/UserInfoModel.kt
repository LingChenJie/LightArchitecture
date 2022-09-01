package com.light.xhd.data.model

import com.light.xhd.data.model.db.AppDBManager
import com.light.xhd.data.model.db.dao.NoteDao
import com.light.xhd.data.model.db.dao.UserInfoDao
import com.light.xhd.data.model.db.entity.Note
import com.light.xhd.data.model.db.entity.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
object UserInfoModel {

    private val dao: UserInfoDao by lazy {
        AppDBManager.instance.getDataBase().userInfoDao()
    }

    suspend fun insert(userInfo: UserInfo): Long {
        val id = withContext(Dispatchers.IO) {
            dao.insert(userInfo)
        }
        return id
    }

    suspend fun update(userInfo: UserInfo): Boolean {
        val update = withContext(Dispatchers.IO) {
            dao.update(userInfo)
        }
        return update > 0
    }

    suspend fun delete(userInfo: UserInfo): Boolean {
        val delete = withContext(Dispatchers.IO) {
            dao.delete(userInfo)
        }
        return delete > 0
    }

    suspend fun getNotes(): List<UserInfo> {
        return withContext(Dispatchers.IO) {
            dao.getAllUser()
        }
    }

    fun getNotesFlow(): Flow<List<UserInfo>> {
        return dao.getAllUserFlow()
    }

}