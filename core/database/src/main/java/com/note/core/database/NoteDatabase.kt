package com.note.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.note.core.database.exam.NoteDao
import com.note.core.database.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        const val DB_FILE_NAME = "note.db"
    }
}