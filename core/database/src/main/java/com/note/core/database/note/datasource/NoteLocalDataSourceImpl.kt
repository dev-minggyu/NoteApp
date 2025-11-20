package com.note.core.database.note.datasource

import com.note.core.database.entity.NoteEntity
import com.note.core.database.note.dao.NoteDao
import com.note.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class NoteLocalDataSourceImpl(
    private val noteDao: NoteDao
) : NoteLocalDataSource {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
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

    override fun getEnabledAlarms(): Flow<List<Note>> {
        return noteDao.getEnabledAlarms().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun toggleAlarm(noteId: Int, isEnabled: Boolean) {
        noteDao.toggleAlarm(noteId, isEnabled)
    }

    override suspend fun updateAlarm(
        noteId: Int,
        alarmTime: LocalDateTime?,
        isEnabled: Boolean,
        message: String?
    ) {
        val alarmTimeMillis = alarmTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
        noteDao.updateAlarm(noteId, alarmTimeMillis, isEnabled, message)
    }

    private fun NoteEntity.toDomain() = Note(
        id = id,
        title = title,
        content = content,
        createdDate = createdDate,
        updatedDate = updatedDate,
        alarmTime = alarmTime?.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
        },
        isAlarmEnabled = isAlarmEnabled,
        alarmMessage = alarmMessage
    )

    private fun Note.toEntity() = NoteEntity(
        id = id,
        title = title,
        content = content,
        createdDate = createdDate,
        updatedDate = updatedDate,
        alarmTime = alarmTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli(),
        isAlarmEnabled = isAlarmEnabled,
        alarmMessage = alarmMessage
    )
}