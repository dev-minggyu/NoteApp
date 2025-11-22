package com.note.feature.notedetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.note.feature.common.extension.dateFormat
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.notedetail.R
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSettingDialog(
    currentAlarmTime: Long?,
    currentAlarmMessage: String?,
    onDismiss: () -> Unit,
    onConfirm: (timeInMillis: Long, message: String) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    val calendar = remember {
        Calendar.getInstance().apply {
            if (currentAlarmTime != null) {
                timeInMillis = currentAlarmTime
            } else {
                add(Calendar.HOUR_OF_DAY, 1)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }
    }

    var selectedDate by remember { mutableStateOf(calendar.time) }
    var selectedHour by remember { mutableIntStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
    var selectedMinute by remember { mutableIntStateOf(calendar.get(Calendar.MINUTE)) }
    var message by remember { mutableStateOf(currentAlarmMessage ?: "") }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate.time)
    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute,
        is24Hour = true
    )

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    AlertDialog(
        containerColor = AppTheme.colors.contentBackground,
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (currentAlarmTime != null) {
                        stringResource(R.string.alarm_edit)
                    } else {
                        stringResource(R.string.alarm_setting)
                    },
                    color = AppTheme.colors.titleText,
                    style = AppTheme.typo.headlineSmall
                )

                if (currentAlarmTime != null && onDelete != null) {
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.alarm_delete),
                            tint = AppTheme.colors.error
                        )
                    }
                }
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showDatePicker = true },
                    colors = CardDefaults.outlinedCardColors(containerColor = AppTheme.colors.contentBackground)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = AppTheme.colors.primary
                            )
                            Column {
                                Text(
                                    text = stringResource(R.string.alarm_date),
                                    fontSize = 12.sp,
                                    color = AppTheme.colors.titleText
                                )
                                Text(
                                    text = selectedDate.dateFormat(stringResource(R.string.date_format_full)),
                                    fontSize = 16.sp,
                                    color = AppTheme.colors.titleText
                                )
                            }
                        }
                    }
                }

                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showTimePicker = true },
                    colors = CardDefaults.outlinedCardColors(containerColor = AppTheme.colors.contentBackground)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = AppTheme.colors.primary
                            )
                            Column {
                                Text(
                                    text = stringResource(R.string.alarm_time),
                                    fontSize = 12.sp,
                                    color = AppTheme.colors.titleText
                                )
                                Text(
                                    text = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute),
                                    fontSize = 16.sp,
                                    color = AppTheme.colors.titleText
                                )
                            }
                        }
                    }
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = message,
                    onValueChange = { message = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.alarm_message_placeholder),
                            color = AppTheme.colors.titleText
                        )
                    },
                    maxLines = 1,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppTheme.colors.primary,
                        unfocusedBorderColor = AppTheme.colors.emptyText
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        color = AppTheme.colors.titleText
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val calendar = Calendar.getInstance().apply {
                        time = selectedDate
                        set(Calendar.HOUR_OF_DAY, selectedHour)
                        set(Calendar.MINUTE, selectedMinute)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    onConfirm(calendar.timeInMillis, message.ifBlank { "" })
                }
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                    color = AppTheme.colors.titleText
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = AppTheme.colors.titleText
                )
            }
        }
    )

    if (showDatePicker) {
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                containerColor = AppTheme.colors.contentBackground,
            ),
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDate = Date(it)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                        color = AppTheme.colors.titleText
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = AppTheme.colors.titleText
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        AlertDialog(
            containerColor = AppTheme.colors.contentBackground,
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedHour = timePickerState.hour
                        selectedMinute = timePickerState.minute
                        showTimePicker = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                        color = AppTheme.colors.titleText
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = AppTheme.colors.titleText
                    )
                }
            },
            text = {
                TimePicker(
                    modifier = Modifier.padding(16.dp),
                    state = timePickerState
                )
            }
        )
    }
}
