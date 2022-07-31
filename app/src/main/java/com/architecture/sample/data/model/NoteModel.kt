package com.architecture.sample.data.model

import com.architecture.sample.data.model.db.AppDBManager
import com.architecture.sample.data.model.db.dao.NoteDao
import com.architecture.sample.data.model.db.entity.Note
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

    suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            dao.insert(note)
        }
    }

    suspend fun update(note: Note) {
        withContext(Dispatchers.IO) {
            dao.update(note)
        }
    }

    suspend fun delete(note: Note) {
        withContext(Dispatchers.IO) {
            dao.delete(note)
        }
    }

    fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

}