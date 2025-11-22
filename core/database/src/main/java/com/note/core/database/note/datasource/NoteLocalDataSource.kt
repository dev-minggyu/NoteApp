package com.note.core.database.note.datasource

import com.note.core.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    fun getAllNotes(): Flow<List<NoteEntity>>
    suspend fun getNoteById(id: Long): NoteEntity?
    suspend fun insertNote(note: NoteEntity): Long
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
    fun searchNotes(query: String): Flow<List<NoteEntity>>
    fun getEnabledAlarms(): Flow<List<NoteEntity>>
}