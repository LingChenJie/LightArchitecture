package com.architecture.light.data.model

import com.architecture.light.data.model.db.AppDBManager
import com.architecture.light.data.model.db.dao.NoteDao
import com.architecture.light.data.model.db.entity.Note
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
object NoteModel {

    private val dao: NoteDao by lazy {
        AppDBManager.instance.getDataBase().noteDao()
    }

    suspend fun insert(note: Note): Long {
        val nId = withContext(Dispatchers.IO) {
            dao.insert(note)
        }
        return nId
    }

    suspend fun update(note: Note): Boolean {
        val update = withContext(Dispatchers.IO) {
            dao.update(note)
        }
        return update > 0
    }

    suspend fun delete(note: Note): Boolean {
        val delete = withContext(Dispatchers.IO) {
            dao.delete(note)
        }
        return delete > 0
    }

    suspend fun getNotes(): List<Note> {
        return withContext(Dispatchers.IO) {
            dao.getNotes()
        }
    }

    fun getNotesFlow(): Flow<List<Note>> {
        return dao.getNotesFlow()
    }

}