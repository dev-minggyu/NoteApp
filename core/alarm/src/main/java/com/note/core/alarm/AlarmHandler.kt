package com.note.core.alarm

import android.content.Context

interface AlarmHandler {
    val type: String
    suspend fun onAlarmFired(context: Context, alarmId: Int)
    suspend fun onBootCompleted()
    fun schedule(alarmId: Int, time: Long, message: String? = null)
    fun cancel(alarmId: Int)
}
