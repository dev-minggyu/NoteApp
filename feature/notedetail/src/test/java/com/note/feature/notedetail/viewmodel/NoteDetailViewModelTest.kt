package com.note.feature.notedetail.viewmodel

import com.note.core.test.testViewModel
import com.note.domain.model.Note
import com.note.domain.repository.NoteRepository
import com.note.feature.notedetail.NoteDetailContract
import com.note.feature.notedetail.alarm.NoteAlarmHandler
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteDetailViewModelTest {

    @get:Rule
    val dispatcherRule = NoteDetailDispatcherRule()

    private lateinit var repository: NoteRepository
    private lateinit var alarmHandler: NoteAlarmHandler
    private lateinit var processor: NoteDetailProcessor
    private lateinit var reducer: NoteDetailReducer

    @Before
    fun setUp() {
        repository = mockk()
        alarmHandler = mockk()
        processor = NoteDetailProcessor(repository, alarmHandler)
        reducer = NoteDetailReducer()
    }

    @Test
    fun `노트_로드_액션을_보내면_노트가_로드되어야_한다`() {
        val noteId = 1L
        val note = Note(
            id = noteId,
            title = "Title",
            content = "Content",
            alarmTime = 1L,
            alarmMessage = "Alarm",
            isAlarmEnabled = true
        )

        testViewModel(
            createViewModel = { NoteDetailViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock {
                    coEvery { repository.getNoteById(noteId) } returns note
                }
            }

            whenBlock {
                action {
                    sendAction(NoteDetailContract.Action.LoadNote(noteId))
                }
            }

            thenBlock {
                lastState {
                    assertEquals(note, current.note)
                    assertEquals(note.title, current.title)
                    assertEquals(note.content, current.content)
                    assertEquals(note.alarmTime, current.alarmTime)
                    assertEquals(note.alarmMessage, current.alarmMessage)
                    assertEquals(note.isAlarmEnabled, current.isAlarmEnabled)
                    assertFalse(current.isLoading)
                }
            }
        }
    }

    @Test
    fun `유효하지_않은_ID로_노트_로드시_에러가_발생해야_한다`() {
        testViewModel(
            createViewModel = { NoteDetailViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action {
                    sendAction(NoteDetailContract.Action.LoadNote(null))
                }
            }

            thenBlock {
                event {
                    assertEquals(NoteDetailContract.Event.ShowError(NoteDetailContract.Event.Error.InvalidNote), this)
                }
                lastState {
                    assertEquals(NoteDetailContract.Event.Error.InvalidNote, current.error)
                }
            }
        }
    }

    @Test
    fun `제목_업데이트_액션을_보내면_상태가_변경되어야_한다`() {
        val newTitle = "New Title"

        testViewModel(
            createViewModel = { NoteDetailViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action {
                    sendAction(NoteDetailContract.Action.UpdateTitle(newTitle))
                }
            }

            thenBlock {
                state {
                    assertEquals(newTitle, current.title)
                }
            }
        }
    }

    @Test
    fun `내용_업데이트_액션을_보내면_상태가_변경되어야_한다`() {
        val newContent = "New Content"

        testViewModel(
            createViewModel = { NoteDetailViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action {
                    sendAction(NoteDetailContract.Action.UpdateContent(newContent))
                }
            }

            thenBlock {
                state {
                    assertEquals(newContent, current.content)
                }
            }
        }
    }

    @Test
    fun `노트_저장_액션을_보내면_저장_성공_이벤트가_발생해야_한다`() {
        val title = "Title"
        val content = "Content"

        testViewModel(
            createViewModel = { NoteDetailViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock {
                    coEvery { repository.insertNote(any()) } returns 1L
                    coEvery { alarmHandler.schedule(any(), any(), any()) } returns Unit
                    coEvery { alarmHandler.cancel(any()) } returns Unit
                }
            }

            whenBlock {
                action {
                    sendAction(NoteDetailContract.Action.UpdateTitle(title))
                    sendAction(NoteDetailContract.Action.UpdateContent(content))
                    sendAction(NoteDetailContract.Action.SaveNote)
                }
            }

            thenBlock {
                event {
                    assertEquals(NoteDetailContract.Event.NoteSaved, this)
                }
                lastState {
                    assertFalse(current.isSaving)
                }
            }
        }
        
        coVerify { repository.insertNote(any()) }
    }

    @Test
    fun `내용이_없는_노트_저장시_에러가_발생해야_한다`() {
        testViewModel(
            createViewModel = { NoteDetailViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action {
                    sendAction(NoteDetailContract.Action.UpdateContent(""))
                    sendAction(NoteDetailContract.Action.SaveNote)
                }
            }

            thenBlock {
                event {
                    assertEquals(NoteDetailContract.Event.ShowError(NoteDetailContract.Event.Error.RequireContent), this)
                }
            }
        }
    }

    @Test
    fun `알람_설정_액션을_보내면_상태가_변경되어야_한다`() {
        val time = 1L
        val message = "Alarm Message"

        testViewModel(
            createViewModel = { NoteDetailViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action {
                    sendAction(NoteDetailContract.Action.SetAlarm(time, message))
                }
            }

            thenBlock {
                state {
                    assertEquals(time, current.alarmTime)
                    assertEquals(message, current.alarmMessage)
                }
            }
        }
    }

    @Test
    fun `알람_토글_액션을_보내면_상태가_변경되어야_한다`() {
        testViewModel(
            createViewModel = { NoteDetailViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action {
                    sendAction(NoteDetailContract.Action.ToggleAlarm(true))
                }
            }

            thenBlock {
                state {
                    assertTrue(current.isAlarmEnabled)
                }
            }
        }
    }

    @Test
    fun `뒤로가기_액션을_보내면_이벤트가_발생해야_한다`() {
        testViewModel(
            createViewModel = { NoteDetailViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action {
                    sendAction(NoteDetailContract.Action.NavigateBack)
                }
            }

            thenBlock {
                event {
                    assertEquals(NoteDetailContract.Event.NavigateBack, this)
                }
            }
        }
    }
}
