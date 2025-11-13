package com.note.app.di

import com.note.app.ui.main.viewmodel.MainProcessor
import com.note.app.ui.main.viewmodel.MainReducer
import com.note.app.ui.main.viewmodel.MainViewModel
import com.note.app.ui.notedetail.viewmodel.NoteDetailProcessor
import com.note.app.ui.notedetail.viewmodel.NoteDetailReducer
import com.note.app.ui.notedetail.viewmodel.NoteDetailViewModel
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