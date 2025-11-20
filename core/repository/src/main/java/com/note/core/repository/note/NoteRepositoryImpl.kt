package com.note.core.repository.note

import com.note.core.database.note.datasource.NoteLocalDataSource
import com.note.domain.model.Note
import com.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class NoteRepositoryImpl(
    private val noteLocalDataSource: NoteLocalDataSource
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteLocalDataSource.getAllNotes()
    }

    override suspend fun getNoteById(noteId: Int): Note? {
        return noteLocalDataSource.getNoteById(noteId)
    }

    override suspend fun insertNote(note: Note): Long {
        return noteLocalDataSource.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        noteLocalDataSource.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteLocalDataSource.deleteNote(note)
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        return noteLocalDataSource.searchNotes(query)
    }

    override fun getEnabledAlarms(): Flow<List<Note>> {
        return noteLocalDataSource.getEnabledAlarms()
    }

    override suspend fun toggleAlarm(noteId: Int, isEnabled: Boolean) {
        noteLocalDataSource.toggleAlarm(noteId, isEnabled)
    }

    override suspend fun updateAlarm(
        noteId: Int,
        alarmTime: LocalDateTime?,
        isEnabled: Boolean,
        message: String?
    ) {
        noteLocalDataSource.updateAlarm(noteId, alarmTime, isEnabled, message)
    }
}