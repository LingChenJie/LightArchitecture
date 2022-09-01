package com.architecture.light.data.model.db.dao

import androidx.room.*
import com.architecture.light.data.model.db.entity.UserInfo
import kotlinx.coroutines.flow.Flow

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userInfo: UserInfo): Long

    @Update
    fun update(userInfo: UserInfo): Int

    @Delete
    fun delete(userInfo: UserInfo): Int

    @Query("DELETE FROM UserInfo")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM UserInfo")
    fun count(): Int

    @Query("SELECT * FROM UserInfo")
    fun getAllUser(): List<UserInfo>

    @Query("SELECT * FROM UserInfo")
    fun getAllUserFlow(): Flow<List<UserInfo>>

    @Query("SELECT * FROM UserInfo WHERE username = :username")
    fun queryUserInfoByUsername(username: String): UserInfo?

    @Query("SELECT * FROM UserInfo WHERE username = :username")
    fun queryUserInfoByUsernameFlow(username: String): Flow<UserInfo>

}