package com.note.domain.model

data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis()
)