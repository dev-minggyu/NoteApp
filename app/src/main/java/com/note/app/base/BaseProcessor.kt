package com.note.app.base

import com.note.app.base.contract.UiAction
import com.note.app.base.contract.UiMutation
import kotlinx.coroutines.flow.Flow

abstract class BaseProcessor<Event : UiAction, Mutation : UiMutation> {
    abstract fun process(event: Event): Flow<Mutation>
}