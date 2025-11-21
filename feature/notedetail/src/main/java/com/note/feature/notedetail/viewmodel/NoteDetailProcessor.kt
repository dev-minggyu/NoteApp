package com.note.feature.notedetail.viewmodel

import com.note.domain.model.Note
import com.note.domain.repository.NoteRepository
import com.note.domain.scheduler.AlarmScheduler
import com.note.feature.common.ui.base.BaseProcessor
import com.note.feature.notedetail.NoteDetailContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteDetailProcessor(
    private val noteRepository: NoteRepository,
    private val alarmScheduler: AlarmScheduler
) : BaseProcessor<NoteDetailContract.Action, NoteDetailContract.Mutation>() {

    private var currentNote: Note? = null
    private var currentTitle: String = ""
    private var currentContent: String = ""

    override fun process(action: NoteDetailContract.Action): Flow<NoteDetailContract.Mutation> {
        return when (action) {
            is NoteDetailContract.Action.LoadNote -> loadNote(action.noteId)
            is NoteDetailContract.Action.UpdateTitle -> updateTitle(action.title)
            is NoteDetailContract.Action.UpdateContent -> updateContent(action.content)
            is NoteDetailContract.Action.SaveNote -> saveNote()
            is NoteDetailContract.Action.NavigateBack -> flow {
                emit(NoteDetailContract.Mutation.NavigateBackMutation)
            }
        }
    }

    private fun loadNote(noteId: Int?): Flow<NoteDetailContract.Mutation> {
        return flow {
            emit(NoteDetailContract.Mutation.ShowProgress)
            if (noteId == null) {
                emit(NoteDetailContract.Mutation.ShowError(NoteDetailContract.Event.Error.InvalidNote))
                return@flow
            }
            runCatching {
                noteRepository.getNoteById(noteId)
            }.onSuccess { note ->
                if (note != null) {
                    currentNote = note
                    currentTitle = note.title
                    currentContent = note.content
                    emit(NoteDetailContract.Mutation.NoteLoaded(note))
                } else {
                    emit(NoteDetailContract.Mutation.ShowError(NoteDetailContract.Event.Error.InvalidNote))
                }
            }.onFailure { error ->
                emit(NoteDetailContract.Mutation.ShowError(NoteDetailContract.Event.Error.InvalidNote))
            }
        }
    }

    private fun updateTitle(title: String): Flow<NoteDetailContract.Mutation> {
        return flow {
            currentTitle = title
            emit(NoteDetailContract.Mutation.TitleUpdated(title))
        }
    }

    private fun updateContent(content: String): Flow<NoteDetailContract.Mutation> {
        return flow {
            currentContent = content
            emit(NoteDetailContract.Mutation.ContentUpdated(content))
        }
    }

    private fun saveNote(): Flow<NoteDetailContract.Mutation> {
        return flow {
            if (currentContent.isBlank()) {
                emit(NoteDetailContract.Mutation.ShowError(NoteDetailContract.Event.Error.RequireContent))
                return@flow
            }

            emit(NoteDetailContract.Mutation.SavingNote)

            runCatching {
                val note = Note(
                    id = currentNote?.id ?: 0,
                    title = currentTitle,
                    content = currentContent,
                    createdDate = currentNote?.createdDate ?: System.currentTimeMillis(),
                    updatedDate = System.currentTimeMillis(),
                    alarmTime = null,
                    isAlarmEnabled = false,
                    alarmMessage = ""
                )

                if (currentNote == null) {
                    noteRepository.insertNote(note)
                } else {
                    noteRepository.updateNote(note)
                }
            }.onSuccess {
                emit(NoteDetailContract.Mutation.NoteSavedSuccess)
            }.onFailure { error ->
                emit(NoteDetailContract.Mutation.ShowError(NoteDetailContract.Event.Error.SaveFailure))
            }
        }
    }
}