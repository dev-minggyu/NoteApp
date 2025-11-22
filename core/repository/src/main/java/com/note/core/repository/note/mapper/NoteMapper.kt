package com.note.core.repository.note.mapper

import com.note.core.database.entity.NoteEntity
import com.note.domain.model.Note

fun NoteEntity.toDomain() = Note(
    id = id,
    title = title,
    content = content,
    createdDate = createdDate,
    updatedDate = updatedDate,
    alarmTime = alarmTime,
    isAlarmEnabled = isAlarmEnabled,
    alarmMessage = alarmMessage
)

fun Note.toEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    createdDate = createdDate,
    updatedDate = updatedDate,
    alarmTime = alarmTime,
    isAlarmEnabled = isAlarmEnabled,
    alarmMessage = alarmMessage
)
