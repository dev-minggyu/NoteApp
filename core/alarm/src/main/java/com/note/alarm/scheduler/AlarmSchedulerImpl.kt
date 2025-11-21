package com.note.alarm.scheduler

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.note.alarm.receiver.AlarmReceiver
import com.note.domain.scheduler.AlarmScheduler
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmSchedulerImpl(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    companion object {
        const val EXTRA_NOTE_ID = "EXTRA_NOTE_ID"
    }

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun schedule(alarmId: Int, time: LocalDateTime, message: String) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_NOTE_ID, alarmId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = time
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun cancel(alarmId: Int) {
        val intent = Intent(context, AlarmReceiver::class.java)
        PendingIntent.getBroadcast(
            context,
            alarmId,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        ).also {
            alarmManager.cancel(it)
        }
    }
}