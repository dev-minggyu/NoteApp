package com.note.alarm.di

import com.note.alarm.scheduler.AlarmSchedulerImpl
import com.note.domain.scheduler.AlarmScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val alarmModule = module {
    single<AlarmScheduler> { AlarmSchedulerImpl(androidContext()) }
}