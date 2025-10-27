package com.note.app.utils.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
fun <Event, State, Mutation> Flow<Event>.reduceToState(
    processor: (Event) -> Flow<Mutation>,
    reducer: (State, Mutation) -> State,
    initialState: State,
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000)
): StateFlow<State> = flatMapLatest(processor)
    .runningFold(initialState, reducer)
    .stateIn(scope, started, initialState)