package com.note.core.repository.note

import com.note.core.database.note.datasource.NoteLocalDataSource
import com.note.core.repository.note.mapper.toDomain
import com.note.core.repository.note.mapper.toEntity
import com.note.domain.model.Note
import com.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteLocalDataSource: NoteLocalDataSource
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteLocalDataSource.getAllNotes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getNoteById(noteId: Long): Note? {
        return noteLocalDataSource.getNoteById(noteId)?.toDomain()
    }

    override suspend fun insertNote(note: Note): Long {
        return noteLocalDataSource.insertNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteLocalDataSource.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteLocalDataSource.deleteNote(note.toEntity())
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        return noteLocalDataSource.searchNotes(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getEnabledAlarms(): Flow<List<Note>> {
        return noteLocalDataSource.getEnabledAlarms().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}