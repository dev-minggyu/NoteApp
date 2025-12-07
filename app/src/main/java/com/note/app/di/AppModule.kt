package com.note.app.di

import com.note.core.alarm.AlarmHandler
import com.note.feature.main.viewmodel.MainProcessor
import com.note.feature.main.viewmodel.MainReducer
import com.note.feature.main.viewmodel.MainViewModel
import com.note.feature.notedetail.alarm.NoteAlarmHandler
import com.note.feature.notedetail.viewmodel.NoteDetailProcessor
import com.note.feature.notedetail.viewmodel.NoteDetailReducer
import com.note.feature.notedetail.viewmodel.NoteDetailViewModel
import com.note.feature.setting.viewmodel.SettingProcessor
import com.note.feature.setting.viewmodel.SettingReducer
import com.note.feature.setting.viewmodel.SettingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get(), get()) }
    factory { MainReducer() }
    factory { MainProcessor(get()) }

    viewModel { NoteDetailViewModel(get(), get()) }
    factory { NoteDetailReducer() }
    factory { NoteDetailProcessor(get(), get()) }

    viewModel { SettingViewModel(get(), get()) }
    factory { SettingReducer() }
    factory { SettingProcessor(get()) }

    single { NoteAlarmHandler(get(), get()) }
    single<List<AlarmHandler>> { listOf(get<NoteAlarmHandler>()) }
}