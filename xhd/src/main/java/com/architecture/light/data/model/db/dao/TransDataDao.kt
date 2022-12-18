package com.architecture.light.data.model.db.dao

import androidx.room.*
import com.architecture.light.data.model.db.entity.TransData

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
@Dao
interface TransDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transData: TransData): Long

    @Update
    fun update(transData: TransData): Int

    @Delete
    fun delete(transData: TransData): Int

    @Query("DELETE FROM TransData")
    fun deleteAll()

    @Query("DELETE FROM TransData WHERE transactionStatus NOT IN ('TransTimeout', 'ResultNotifyFailed') & transactionTimeMillis < :oldTimeMillis")
    fun deleteOldData(oldTimeMillis: Long): Int

    @Query("SELECT COUNT(*) FROM TransData")
    fun getCount(): Int

    @Query("SELECT * FROM TransData ORDER BY tId DESC")
    fun getAll(): List<TransData>

    @Query("SELECT * FROM TransData WHERE tId = :id")
    fun queryById(id: Long): TransData?

    @Query("SELECT * FROM TransData WHERE voucherNumber = :voucherNumber ORDER BY tId DESC")
    fun queryByVoucher(voucherNumber: String): List<TransData>

    @Query("SELECT * FROM TransData WHERE transactionName IN ('Payment', 'Reserve', 'AdvancesReceived') AND transactionStatus IN ('TransTimeout', 'TransSucceed', 'ResultNotifyFailed') ORDER BY tId DESC")
    fun queryPaymentTimeout2SyncFailedTrans(): List<TransData>

    @Query("SELECT * FROM TransData WHERE transactionName = 'Void' AND transactionStatus IN ('TransTimeout', 'TransSucceed', 'ResultNotifyFailed') ORDER BY tId DESC")
    fun queryVoidTimeout2SyncFailedTrans(): List<TransData>

}