package com.note.feature.notedetail.alarm

import android.content.Context
import com.note.core.alarm.AlarmHandler
import com.note.core.alarm.notification.AlarmNotificationService
import com.note.domain.repository.NoteRepository
import com.note.domain.scheduler.AlarmScheduler
import kotlinx.coroutines.flow.firstOrNull

class NoteAlarmHandler(
    private val noteRepository: NoteRepository,
    private val alarmScheduler: AlarmScheduler
) : AlarmHandler {

    override val type: String = "NOTE_ALARM"

    override fun schedule(alarmId: Int, time: Long, message: String?) {
        alarmScheduler.schedule(alarmId, time, type, message)
    }

    override fun cancel(alarmId: Int) {
        alarmScheduler.cancel(alarmId)
    }

    override suspend fun onAlarmFired(context: Context, alarmId: Int) {
        try {
            val note = noteRepository.getNoteById(alarmId.toLong())
            if (note != null && note.isAlarmEnabled) {
                AlarmNotificationService.showNotification(
                    context = context,
                    noteId = alarmId.toLong(),
                    title = note.title,
                    message = note.alarmMessage
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun onBootCompleted() {
        try {
            val enabledAlarms = noteRepository.getEnabledAlarms().firstOrNull()
            enabledAlarms?.forEach { note ->
                schedule(note.id.toInt(), note.alarmTime, note.alarmMessage)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
