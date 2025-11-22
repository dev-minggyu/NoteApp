package com.note.core.database.note.datasource

import com.note.core.database.entity.NoteEntity
import com.note.core.database.note.dao.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteLocalDataSourceImpl(
    private val noteDao: NoteDao
) : NoteLocalDataSource {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    override suspend fun getNoteById(id: Long): NoteEntity? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: NoteEntity): Long {
        return noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }

    override fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return noteDao.searchNotes(query)
    }

    override fun getEnabledAlarms(): Flow<List<NoteEntity>> {
        return noteDao.getEnabledAlarms()
    }
}