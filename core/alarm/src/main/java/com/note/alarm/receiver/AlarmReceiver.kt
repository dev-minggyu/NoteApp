package com.note.alarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.note.alarm.notification.AlarmNotificationService
import com.note.alarm.scheduler.AlarmSchedulerImpl
import com.note.domain.repository.NoteRepository
import com.note.domain.scheduler.AlarmScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlarmReceiver : BroadcastReceiver(), KoinComponent {

    private val noteRepository: NoteRepository by inject()
    private val alarmScheduler: AlarmScheduler by inject()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> handleBootCompleted()
            else -> handleAlarm(context, intent)
        }
    }

    private fun handleAlarm(context: Context, intent: Intent) {
        val noteId = intent.getIntExtra(AlarmSchedulerImpl.EXTRA_NOTE_ID, -1)
        scope.launch {
            try {
                val note = noteRepository.getNoteById(noteId)
                if (note != null && note.isAlarmEnabled) {
                    AlarmNotificationService.showNotification(
                        context = context,
                        noteId = noteId,
                        title = note.title,
                        message = note.alarmMessage
                    )
                    noteRepository.toggleAlarm(noteId, false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun handleBootCompleted() {
        scope.launch {
            try {
                val enabledAlarms = noteRepository.getEnabledAlarms().firstOrNull()
                enabledAlarms?.forEach { note ->
                    note.alarmTime?.let { alarmTime ->
                        alarmScheduler.schedule(note.id, alarmTime, note.alarmMessage)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}