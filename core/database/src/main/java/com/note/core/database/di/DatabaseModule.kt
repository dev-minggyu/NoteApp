package com.note.core.database.di

import androidx.room.Room
import com.note.core.database.NoteDatabase
import com.note.core.database.note.datasource.NoteLocalDataSource
import com.note.core.database.note.datasource.NoteLocalDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NoteDatabase::class.java,
            NoteDatabase.DB_FILE_NAME
        ).build()
    }

    single { get<NoteDatabase>().noteDao() }

    single<NoteLocalDataSource> { NoteLocalDataSourceImpl(get()) }
}