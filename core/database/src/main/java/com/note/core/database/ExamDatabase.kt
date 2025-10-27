package com.note.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.note.core.database.exam.ExamDao
import com.note.core.database.model.ExamEntity

@Database(entities = [ExamEntity::class], version = 1, exportSchema = false)
abstract class ExamDatabase : RoomDatabase() {
    abstract fun examDao(): ExamDao

    companion object {
        const val DB_FILE_NAME = "exam.db"
    }
} 