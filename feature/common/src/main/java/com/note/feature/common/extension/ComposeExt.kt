package com.note.feature.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.note.feature.common.ui.base.contract.UiEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun <T : UiEvent> HandleEvents(
    eventFlow: Flow<T>,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(eventFlow, lifecycleOwner) {
        eventFlow
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect(onEvent)
    }
}