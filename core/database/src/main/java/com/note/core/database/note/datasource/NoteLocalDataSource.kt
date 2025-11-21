package com.note.core.database.note.datasource

import com.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Long): Note?
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun searchNotes(query: String): Flow<List<Note>>
    fun getEnabledAlarms(): Flow<List<Note>>
}