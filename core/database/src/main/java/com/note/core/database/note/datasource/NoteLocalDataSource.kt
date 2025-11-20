package com.note.core.database.note.datasource

import com.note.domain.model.Note
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface NoteLocalDataSource {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun searchNotes(query: String): Flow<List<Note>>
    fun getEnabledAlarms(): Flow<List<Note>>
    suspend fun toggleAlarm(noteId: Int, isEnabled: Boolean)
    suspend fun updateAlarm(noteId: Int, alarmTime: LocalDateTime?, isEnabled: Boolean, message: String?)
}