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

    @Query("DELETE FROM TransData WHERE transactionStatus in ('PaySucceed', 'PayFailed', 'ResultNotifySucceed', 'GetPrintDataSucceed','GetPrintDataFailed','PrintSucceed','PrintFailed') & transactionTimeMillis < :oldTimeMillis")
    fun deleteOldSuccessData(oldTimeMillis: Long): Int

    @Query("SELECT COUNT(*) FROM TransData")
    fun count(): Int

    @Query("SELECT * FROM TransData")
    fun getAll(): List<TransData>

    @Query("SELECT * FROM TransData WHERE tId = :id")
    fun queryById(id: Long): TransData?

    @Query("SELECT * FROM TransData WHERE voucherNumber = :voucherNumber")
    fun queryByVoucher(voucherNumber: String): TransData?

}