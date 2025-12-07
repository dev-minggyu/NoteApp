package com.note.core.repository.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.note.core.repository.note.NoteRepositoryImpl
import com.note.core.repository.settings.SettingsRepositoryImpl
import com.note.domain.repository.NoteRepository
import com.note.domain.repository.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val repositoryModule = module {
    single<DataStore<Preferences>> {
        androidContext().dataStore
    }
    single<NoteRepository> {
        NoteRepositoryImpl(get())
    }
    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }
}