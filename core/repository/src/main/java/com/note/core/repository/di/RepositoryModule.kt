package com.note.core.repository.di

import com.note.core.repository.exam.ExamRepositoryImpl
import com.note.domain.repository.ExamRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ExamRepository> {
        ExamRepositoryImpl(get(), get())
    }
}