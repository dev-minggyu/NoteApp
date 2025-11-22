package com.note.app

import android.app.Application
import com.note.core.alarm.di.alarmModule
import com.note.app.di.appModule
import com.note.core.database.di.databaseModule
import com.note.core.network.di.networkModule
import com.note.core.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                appModule,
                networkModule,
                databaseModule,
                repositoryModule,
                alarmModule
            )
        }
    }
}