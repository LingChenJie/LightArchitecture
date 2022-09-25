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

    @Query("DELETE FROM TransData WHERE transactionStatus not in ('TransTimeout', 'ResultNotifyFailed') & transactionTimeMillis < :oldTimeMillis")
    fun deleteOldData(oldTimeMillis: Long): Int

    @Query("SELECT COUNT(*) FROM TransData")
    fun getCount(): Int

    @Query("SELECT * FROM TransData")
    fun getAll(): List<TransData>

    @Query("SELECT * FROM TransData WHERE tId = :id")
    fun queryById(id: Long): TransData?

    @Query("SELECT * FROM TransData WHERE voucherNumber = :voucherNumber")
    fun queryByVoucher(voucherNumber: String): TransData?

    @Query("SELECT * FROM TransData WHERE transactionName = 'Payment' & transactionStatus in('TransTimeout', 'ResultNotifyFailed')")
    fun queryPaymentTimeout2SyncFailedTrans(): List<TransData>

    @Query("SELECT * FROM TransData WHERE transactionName = 'Void' & transactionStatus in('TransTimeout', 'ResultNotifyFailed')")
    fun queryVoidTimeout2SyncFailedTrans(): List<TransData>

}