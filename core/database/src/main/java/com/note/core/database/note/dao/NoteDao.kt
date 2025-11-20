package com.note.core.database.note.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.note.core.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY updatedDate DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isAlarmEnabled = 1 AND alarmTime IS NOT NULL ORDER BY alarmTime ASC")
    fun getEnabledAlarms(): Flow<List<NoteEntity>>

    @Query("UPDATE notes SET isAlarmEnabled = :isEnabled WHERE id = :noteId")
    suspend fun toggleAlarm(noteId: Int, isEnabled: Boolean)

    @Query("UPDATE notes SET alarmTime = :alarmTime, isAlarmEnabled = :isEnabled, alarmMessage = :message WHERE id = :noteId")
    suspend fun updateAlarm(noteId: Int, alarmTime: Long?, isEnabled: Boolean, message: String?)
}