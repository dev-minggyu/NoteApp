package com.note.feature.notedetail.component

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.notedetail.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AlarmPermissionDialog(
    onDismiss: () -> Unit,
    onAllPermissionsGranted: () -> Unit
) {
    val context = LocalContext.current
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
    } else null

    var canScheduleExactAlarms by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                alarmManager.canScheduleExactAlarms()
            } else {
                true
            }
        )
    }

    val exactAlarmLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            canScheduleExactAlarms = alarmManager.canScheduleExactAlarms()
        }
    }

    val allPermissionsGranted = remember(
        notificationPermissionState?.status,
        canScheduleExactAlarms
    ) {
        val notificationGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionState?.status?.isGranted == true
        } else true

        notificationGranted && canScheduleExactAlarms
    }

    LaunchedEffect(allPermissionsGranted) {
        if (allPermissionsGranted) {
            onAllPermissionsGranted()
        }
    }

    AlertDialog(
        containerColor = AppTheme.colors.contentBackground,
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = AppTheme.colors.primary
            )
        },
        title = {
            Text(
                text = stringResource(R.string.alarm_permission_title),
                color = AppTheme.colors.titleText,
                style = AppTheme.typo.headlineSmall
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.alarm_permission_description),
                    color = AppTheme.colors.titleText,
                    fontSize = 14.sp
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && notificationPermissionState != null) {
                    PermissionCard(
                        icon = Icons.Default.Notifications,
                        title = stringResource(R.string.notification_permission),
                        description = stringResource(R.string.notification_permission_description),
                        isGranted = notificationPermissionState.status.isGranted,
                        buttonText = when {
                            notificationPermissionState.status.isGranted ->
                                stringResource(R.string.permission_granted)

                            notificationPermissionState.status.shouldShowRationale ->
                                stringResource(R.string.permission_request_again)

                            else ->
                                stringResource(R.string.permission_allow)
                        },
                        onButtonClick = if (!notificationPermissionState.status.isGranted) {
                            { notificationPermissionState.launchPermissionRequest() }
                        } else null
                    )
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PermissionCard(
                        icon = Icons.Default.Schedule,
                        title = stringResource(R.string.exact_alarm_permission),
                        description = stringResource(R.string.exact_alarm_permission_description),
                        isGranted = canScheduleExactAlarms,
                        buttonText = if (canScheduleExactAlarms) {
                            stringResource(R.string.permission_granted)
                        } else {
                            stringResource(R.string.go_to_settings)
                        },
                        onButtonClick = if (!canScheduleExactAlarms) {
                            {
                                Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                                    data = Uri.parse("package:${context.packageName}")
                                }.also { exactAlarmLauncher.launch(it) }
                            }
                        } else null
                    )
                }
            }
        },
        confirmButton = {
            if (allPermissionsGranted) {
                Button(onClick = onAllPermissionsGranted) {
                    Text(
                        text = stringResource(R.string.confirm),
                        color = AppTheme.colors.titleText
                    )
                }
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
}

@Composable
private fun PermissionCard(
    icon: ImageVector,
    title: String,
    description: String,
    isGranted: Boolean,
    buttonText: String,
    onButtonClick: (() -> Unit)?
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isGranted) {
                AppTheme.colors.contentBackground
            } else {
                AppTheme.colors.error.copy(alpha = 0.3f)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isGranted) AppTheme.colors.primary else AppTheme.colors.error
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    fontSize = 14.sp,
                    color = AppTheme.colors.titleText
                )
                if (isGranted) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = AppTheme.colors.primary
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = AppTheme.colors.error
                    )
                }
            }

            Text(
                text = description,
                fontSize = 12.sp,
                color = AppTheme.colors.titleText
            )

            if (onButtonClick != null) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onButtonClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isGranted) AppTheme.colors.primary else AppTheme.colors.error
                    )
                ) {
                    Text(
                        text = buttonText,
                        color = AppTheme.colors.titleText
                    )
                }
            } else if (isGranted) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buttonText,
                    fontSize = 12.sp,
                    color = AppTheme.colors.titleText
                )
            }
        }
    }
}
