package com.note.core.database.datasource.exam

import com.note.core.database.exam.NoteDao
import com.note.core.database.model.NoteEntity
import com.note.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteLocalDataSourceImpl(
    private val noteDao: NoteDao
) : NoteLocalDataSource {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return noteDao.getNoteById(id)?.toDomain()
    }

    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        return noteDao.searchNotes(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    private fun NoteEntity.toDomain() = Note(
        id = id,
        title = title,
        content = content,
        createdDate = createdDate,
        updatedDate = updatedDate
    )

    private fun Note.toEntity() = NoteEntity(
        id = id,
        title = title,
        content = content,
        createdDate = createdDate,
        updatedDate = updatedDate
    )
}