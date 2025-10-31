package com.note.app.ui.main

import com.note.app.base.BaseProcessor
import com.note.domain.model.Note
import com.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class MainProcessor(
    private val repository: NoteRepository
) : BaseProcessor<MainContract.Action, MainContract.Mutation>() {
    override fun process(action: MainContract.Action): Flow<MainContract.Mutation> {
        return when (action) {
            is MainContract.Action.Stream.ObserveNotes -> observeNotes()
            is MainContract.Action.ToggleListMode -> flow {
                emit(MainContract.Mutation.ToggleView)
            }

            is MainContract.Action.DeleteNote -> deleteNote(action.note)
            is MainContract.Action.NavigateToDetail -> flow {
                emit(MainContract.Mutation.NavigateToDetailMutation(action.noteId))
            }
        }
    }

    private fun observeNotes(): Flow<MainContract.Mutation> {
        return repository.getAllNotes()
            .map<List<Note>, MainContract.Mutation> { notes ->
                MainContract.Mutation.NoteLoaded(notes)
            }
            .onStart {
                emit(MainContract.Mutation.ShowProgress)
            }
            .catch { error ->
                emit(MainContract.Mutation.ShowError(MainContract.Event.Error.InvalidNote))
            }
    }

    private fun deleteNote(note: Note): Flow<MainContract.Mutation> {
        return flow {
            runCatching {
                repository.deleteNote(note)
            }.onSuccess {
                emit(MainContract.Mutation.NoteDeleted(note))
            }.onFailure { error ->
                emit(MainContract.Mutation.ShowError(MainContract.Event.Error.DeleteFailure))
            }
        }
    }
}