package com.architecture.sample.data.model.db.dao

import androidx.room.*
import com.architecture.sample.data.model.db.entity.Note
import kotlinx.coroutines.flow.Flow

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
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

    @Query("SELECT * FROM note ORDER BY type & 0x0001 = 0x0001 DESC, modify_time DESC")
    fun getNotes(): Flow<List<Note>>

}