package com.note.domain.repository

import com.note.domain.model.Note
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(noteId: Int): Note?
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun searchNotes(query: String): Flow<List<Note>>
    fun getEnabledAlarms(): Flow<List<Note>>
    suspend fun toggleAlarm(noteId: Int, isEnabled: Boolean)
    suspend fun updateAlarm(noteId: Int, alarmTime: LocalDateTime?, isEnabled: Boolean, message: String?)
}