package com.note.feature.common.ui.base

import com.note.feature.common.ui.base.contract.UiAction
import com.note.feature.common.ui.base.contract.UiMutation
import kotlinx.coroutines.flow.Flow

abstract class BaseProcessor<Action : UiAction, Mutation : UiMutation> {
    abstract fun process(action: Action): Flow<Mutation>
}