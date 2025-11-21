package com.note.domain.model

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis(),
    val alarmTime: Long = -1L,
    val isAlarmEnabled: Boolean = false,
    val alarmMessage: String = ""
)