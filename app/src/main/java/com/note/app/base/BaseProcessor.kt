package com.note.app.base

import com.note.app.base.contract.UiEvent
import com.note.app.base.contract.UiMutation
import kotlinx.coroutines.flow.Flow

abstract class BaseProcessor<Event : UiEvent, Mutation : UiMutation> {
    abstract fun process(event: Event): Flow<Mutation>
}