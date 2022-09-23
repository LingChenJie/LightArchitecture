package com.architecture.light.data.model

import com.android.architecture.helper.Logger
import com.architecture.light.app.App
import com.architecture.light.data.model.db.AppDBManager
import com.architecture.light.data.model.db.dao.TransDataDao
import com.architecture.light.data.model.db.entity.TransData

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
object TransDataModel {

    private val dao: TransDataDao by lazy {
        AppDBManager.instance.getDataBase().transDataDao()
    }

    fun insert(transData: TransData): Boolean {
        val id = dao.insert(transData)
        transData.tId = id
        Logger.e(App.TAG, "insertTransData id:$id")
        return id > 0
    }

    fun update(transData: TransData): Boolean {
        val id = transData.tId
        return if (id == 0L) {
            Logger.e(App.TAG, "The updated object does not exist.")
            false
        } else {
            val update = dao.update(transData)
            update > 0
        }
    }

    fun delete(transData: TransData): Boolean {
        val delete = dao.delete(transData)
        return delete > 0
    }

    fun deleteAll() {
        dao.deleteAll()
    }

    fun deleteOldSuccessData(oldDay: Int) {
        val dayTimeMillis = 24 * 60 * 60 * 1000
        val oldTimeMillis = System.currentTimeMillis() - oldDay * dayTimeMillis
        dao.deleteOldSuccessData(oldTimeMillis)
    }

    fun getAll(): List<TransData> {
        return dao.getAll()
    }

    fun queryById(id: Long): TransData? {
        return dao.queryById(id)
    }

    fun queryByVoucher(voucherNumber: String): TransData? {
        return dao.queryByVoucher(voucherNumber)
    }

}