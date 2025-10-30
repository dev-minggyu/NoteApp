package com.note.app.di

import com.note.app.ui.main.MainProcessor
import com.note.app.ui.main.MainReducer
import com.note.app.ui.main.MainViewModel
import com.note.app.ui.notedetail.NoteDetailProcessor
import com.note.app.ui.notedetail.NoteDetailReducer
import com.note.app.ui.notedetail.NoteDetailViewModel
import com.note.app.ui.splash.InitProcessor
import com.note.app.ui.splash.InitReducer
import com.note.app.ui.splash.InitViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { InitViewModel(get(), get()) }
    factory { InitReducer() }
    factory { InitProcessor() }

    viewModel { MainViewModel(get(), get()) }
    factory { MainReducer() }
    factory { MainProcessor(get()) }

    viewModel { NoteDetailViewModel(get(), get()) }
    factory { NoteDetailReducer() }
    factory { NoteDetailProcessor(get()) }
}