package com.note.core.repository.di

import com.note.core.repository.exam.NoteRepositoryImpl
import com.note.domain.repository.NoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<NoteRepository> {
        NoteRepositoryImpl(get())
    }
}