package com.note.app.ui.main

import com.note.app.base.BaseProcessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class MainProcessor : BaseProcessor<MainContract.Event, MainContract.Mutation>() {
    override fun process(event: MainContract.Event): Flow<MainContract.Mutation> {
        return when (event) {
            MainContract.Event.LoadData -> flow {
                emit(MainContract.Mutation.ShowLoader)
                delay(500) // mock network
                runCatching {
                    listOf("Item 1", "Item 2", "Item 3")
                }.onSuccess {
                    emit(MainContract.Mutation.ShowContent(it))
                }.onFailure {
                    emit(MainContract.Mutation.ShowError("로드 실패"))
                }
            }

            is MainContract.Event.ClickItem -> {
                flowOf(MainContract.Mutation.ItemClicked(event.id))
            }
        }
    }
}