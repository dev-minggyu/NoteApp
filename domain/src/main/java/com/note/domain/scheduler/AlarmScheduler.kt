package com.note.domain.scheduler

interface AlarmScheduler {
    fun schedule(alarmId: Int, time: Long, message: String)
    fun cancel(alarmId: Int)
}