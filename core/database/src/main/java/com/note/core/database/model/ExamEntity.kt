package com.note.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exam")
data class ExamEntity(
    @PrimaryKey(autoGenerate = false)
    val exam: String,
) 