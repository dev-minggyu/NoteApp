package com.note.core.alarm.di

import com.note.core.alarm.scheduler.AlarmSchedulerImpl
import com.note.domain.scheduler.AlarmScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val alarmModule = module {
    single<AlarmScheduler> { AlarmSchedulerImpl(androidContext()) }
}