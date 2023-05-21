package com.architecture.light.data.local.db.dao

import androidx.room.*
import com.architecture.light.data.local.db.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Long

    @Update
    fun update(note: Note): Int

    @Delete
    fun delete(note: Note): Int

    @Query("DELETE FROM note")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM note")
    fun count(): Int

    @Query("SELECT * FROM note WHERE type != 0x0004 ORDER BY type & 0x0001 = 0x0001 DESC, modify_time DESC")
    fun getNotes(): List<Note>

    @Query("SELECT * FROM note WHERE type = 0x0004 ORDER BY type & 0x0001 = 0x0001 DESC, modify_time DESC")
    fun getLockNotes(): List<Note>

    @Query("SELECT * FROM note ORDER BY type & 0x0001 = 0x0001 DESC, modify_time DESC")
    fun getNotesFlow(): Flow<List<Note>>

}