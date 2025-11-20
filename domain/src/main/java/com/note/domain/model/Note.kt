package com.note.domain.model

import java.time.LocalDateTime

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis(),
    val alarmTime: LocalDateTime? = null,
    val isAlarmEnabled: Boolean = false,
    val alarmMessage: String
)