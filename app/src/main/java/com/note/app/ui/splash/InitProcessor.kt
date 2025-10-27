package com.note.app.ui.splash

import com.note.app.base.BaseProcessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InitProcessor : BaseProcessor<InitContract.Event, InitContract.Mutation>() {
    override fun process(event: InitContract.Event): Flow<InitContract.Mutation> {
        return when (event) {
            InitContract.Event.Initialize -> flow {
                delay(1000)
                emit(InitContract.Mutation.InitComplete)
            }
        }
    }
}