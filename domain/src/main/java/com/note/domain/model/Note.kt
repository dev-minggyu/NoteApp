package com.note.domain.model

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis(),
    val alarmTime: Long? = null,
    val isAlarmEnabled: Boolean = false,
    val alarmMessage: String? = null
)