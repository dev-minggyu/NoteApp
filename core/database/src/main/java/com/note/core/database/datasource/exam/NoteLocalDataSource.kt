package com.note.core.database.datasource.exam

import com.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Long): Note?
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun searchNotes(query: String): Flow<List<Note>>
}