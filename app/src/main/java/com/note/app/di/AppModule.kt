package com.note.app.di

import com.note.feature.main.viewmodel.MainProcessor
import com.note.feature.main.viewmodel.MainReducer
import com.note.feature.main.viewmodel.MainViewModel
import com.note.feature.notedetail.viewmodel.NoteDetailProcessor
import com.note.feature.notedetail.viewmodel.NoteDetailReducer
import com.note.feature.notedetail.viewmodel.NoteDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get(), get()) }
    factory { MainReducer() }
    factory { MainProcessor(get()) }

    viewModel { NoteDetailViewModel(get(), get()) }
    factory { NoteDetailReducer() }
    factory { NoteDetailProcessor(get()) }
}