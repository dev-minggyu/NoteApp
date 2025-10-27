package com.note.app.di

import com.note.app.ui.main.MainProcessor
import com.note.app.ui.main.MainReducer
import com.note.app.ui.main.MainViewModel
import com.note.app.ui.splash.InitProcessor
import com.note.app.ui.splash.InitReducer
import com.note.app.ui.splash.InitViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Processors
    factory { InitProcessor() }
    factory { MainProcessor() }
    
    // Reducers
    factory { InitReducer() }
    factory { MainReducer() }
    
    // ViewModels
    viewModel { InitViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
}