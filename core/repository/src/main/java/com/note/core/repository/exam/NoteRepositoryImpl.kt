package com.note.core.repository.exam

import com.note.core.database.datasource.exam.NoteLocalDataSource
import com.note.domain.model.Note
import com.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteLocalDataSource: NoteLocalDataSource
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteLocalDataSource.getAllNotes()
    }

    override suspend fun getNoteById(id: Long): Note? {
        return noteLocalDataSource.getNoteById(id)
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
}