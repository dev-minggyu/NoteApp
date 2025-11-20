package com.note.alarm.scheduler

import java.time.LocalDateTime

interface AlarmScheduler {
    fun schedule(alarmId: Int, time: LocalDateTime, message: String)
    fun cancel(alarmId: Int)
}