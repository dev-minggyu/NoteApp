package com.note.core.alarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.note.core.alarm.AlarmHandler
import com.note.core.alarm.scheduler.AlarmSchedulerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlarmReceiver : BroadcastReceiver(), KoinComponent {

    private val alarmHandlers: List<AlarmHandler> by inject()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> handleBootCompleted()
            else -> handleAlarm(context, intent)
        }
    }

    private fun handleAlarm(context: Context, intent: Intent) {
        val alarmId = intent.getIntExtra(AlarmSchedulerImpl.EXTRA_ALARM_ID, -1)
        val type = intent.getStringExtra(AlarmSchedulerImpl.EXTRA_ALARM_TYPE)

        if (alarmId != -1 && type != null) {
            scope.launch {
                alarmHandlers.find { it.type == type }?.onAlarmFired(context, alarmId)
            }
        }
    }

    private fun handleBootCompleted() {
        scope.launch {
            alarmHandlers.forEach { it.onBootCompleted() }
        }
    }
}