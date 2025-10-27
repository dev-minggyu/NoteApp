package com.note.app.ui.splash

import com.note.app.base.BaseProcessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InitProcessor : BaseProcessor<InitContract.Action, InitContract.Mutation>() {
    override fun process(event: InitContract.Action): Flow<InitContract.Mutation> {
        return when (event) {
            InitContract.Action.Initialize -> flow {
                delay(1000)
                emit(InitContract.Mutation.InitComplete)
            }
        }
    }
}