package com.note.feature.common.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.dateFormat(pattern: String): String {
    val locale = Locale.getDefault()
    val dateFormat = SimpleDateFormat(pattern, locale)
    return dateFormat.format(Date(this))
}

fun Date.dateFormat(pattern: String): String {
    val locale = Locale.getDefault()
    val dateFormat = SimpleDateFormat(pattern, locale)
    return dateFormat.format(this)
}