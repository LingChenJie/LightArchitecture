package com.architecture.light.data.local

import com.architecture.light.data.local.db.AppDBManager
import com.architecture.light.data.local.db.dao.NoteDao
import com.architecture.light.data.local.db.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

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

    suspend fun getLockNotes(): List<Note> {
        return withContext(Dispatchers.IO) {
            dao.getLockNotes()
        }
    }

    fun getNotesFlow(): Flow<List<Note>> {
        return dao.getNotesFlow()
    }

}