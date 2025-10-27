package com.note.app.utils.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.note.app.base.contract.UiEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun <T : UiEvent> HandleEffects(
    effectFlow: Flow<T>,
    onEffect: (T) -> Unit
) {
    LaunchedEffect(Unit) {
        effectFlow.collect(onEffect)
    }
}