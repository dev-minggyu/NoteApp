package com.note.feature.notedetail.component

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.note.feature.common.extension.dateFormat
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.notedetail.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NoteAlarmSection(
    alarmTime: Long?,
    isAlarmEnabled: Boolean,
    onToggleAlarm: (isEnabled: Boolean) -> Unit,
    onSetAlarm: (timeInMillis: Long, message: String) -> Unit,
    onDeleteAlarm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
    } else null

    val canScheduleExactAlarms by remember {
        derivedStateOf {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                alarmManager.canScheduleExactAlarms()
            } else true
        }
    }

    val hasAllPermissions by remember {
        derivedStateOf {
            val notificationGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationPermissionState?.status?.isGranted == true
            } else true
            notificationGranted && canScheduleExactAlarms
        }
    }

    var showPermissionDialog by remember { mutableStateOf(false) }
    var showAlarmDialog by remember { mutableStateOf(false) }

    var setAlarmTime by remember { mutableStateOf(alarmTime) }

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                if (hasAllPermissions) {
                    showAlarmDialog = true
                } else {
                    showPermissionDialog = true
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = if (isAlarmEnabled) {
                AppTheme.colors.primary
            } else {
                AppTheme.colors.contentBackground
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = if (isAlarmEnabled) Icons.Default.Alarm else Icons.Default.AlarmOff,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = stringResource(R.string.alarm_title),
                        fontSize = 16.sp
                    )
                    Text(
                        text = alarmTime?.dateFormat(stringResource(R.string.datetime_format))
                            ?: stringResource(R.string.alarm_not_set),
                        fontSize = 12.sp,
                    )
                }
            }

            if (setAlarmTime != null) {
                Switch(
                    checked = isAlarmEnabled,
                    onCheckedChange = { onToggleAlarm(it) }
                )
            }
        }
    }

    if (showPermissionDialog) {
        AlarmPermissionDialog(
            onDismiss = { showPermissionDialog = false },
            onAllPermissionsGranted = {
                showPermissionDialog = false
                showAlarmDialog = true
            }
        )
    }

    if (showAlarmDialog) {
        AlarmSettingDialog(
            currentAlarmTime = alarmTime,
            onDismiss = { showAlarmDialog = false },
            onConfirm = { time, message ->
                onSetAlarm(time, message)
                setAlarmTime = time
                showAlarmDialog = false
            },
            onDelete = {
                onDeleteAlarm()
                setAlarmTime = null
                showAlarmDialog = false
            }
        )
    }
}