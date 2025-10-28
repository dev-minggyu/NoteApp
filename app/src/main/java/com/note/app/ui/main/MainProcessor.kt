package com.note.app.ui.main

import com.note.app.base.BaseProcessor
import com.note.domain.model.Note
import com.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MainProcessor(
    private val repository: NoteRepository
) : BaseProcessor<MainContract.Action, MainContract.Mutation>() {
    override fun process(event: MainContract.Action): Flow<MainContract.Mutation> {
        return when (event) {
            is MainContract.Action.LoadNotes -> loadNotes()
            is MainContract.Action.ToggleViewMode -> flow {
                emit(MainContract.Mutation.ToggleView)
            }

            is MainContract.Action.DeleteNote -> deleteNote(event.note)
            is MainContract.Action.NavigateToDetail -> flow {
                emit(MainContract.Mutation.NavigateToDetailMutation(event.noteId))
            }
        }
    }

    private fun loadNotes(): Flow<MainContract.Mutation> {
        return flow {
            emit(MainContract.Mutation.ShowLoader)
            repository.getAllNotes()
                .map<List<Note>, MainContract.Mutation> { notes ->
                    MainContract.Mutation.ShowContent(notes)
                }
                .catch { error ->
                    emit(MainContract.Mutation.ShowError(error.message ?: "메모 로드 실패"))
                }
                .collect { mutation ->
                    emit(mutation)
                }
        }
    }

    private fun deleteNote(note: Note): Flow<MainContract.Mutation> {
        return flow {
            runCatching {
                repository.deleteNote(note)
            }.onSuccess {
                emit(MainContract.Mutation.NoteDeleted(note))
            }.onFailure { error ->
                emit(MainContract.Mutation.ShowError(error.message ?: "메모 삭제 실패"))
            }
        }
    }
}