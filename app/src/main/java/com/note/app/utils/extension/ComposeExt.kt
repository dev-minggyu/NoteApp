package com.note.app.utils.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.note.app.base.contract.UiEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun <T : UiEvent> HandleEffects(
    effectFlow: Flow<T>,
    onEffect: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(effectFlow, lifecycleOwner) {
        effectFlow
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect(onEffect)
    }
}