package com.architecture.light.data.model

import com.architecture.light.data.model.db.AppDBManager
import com.architecture.light.data.model.db.dao.UserInfoDao
import com.architecture.light.data.model.db.entity.UserInfo
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

    suspend fun getAllUser(): List<UserInfo> {
        return withContext(Dispatchers.IO) {
            dao.getAllUser()
        }
    }

    fun queryUserInfoByUsername(username: String): UserInfo? {
        return dao.queryUserInfoByUsername(username)
    }

    fun queryUserInfoByUsernameFlow(username: String): Flow<UserInfo> {
        return dao.queryUserInfoByUsernameFlow(username)
    }

}