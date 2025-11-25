package com.note.domain.scheduler

interface AlarmScheduler {
    fun schedule(alarmId: Int, time: Long, type: String, message: String?)
    fun cancel(alarmId: Int)
}