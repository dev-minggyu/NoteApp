package com.note.feature.main.viewmodel

import com.note.core.test.testViewModel
import com.note.domain.model.Note
import com.note.domain.repository.NoteRepository
import com.note.feature.main.MainContract
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: NoteRepository
    private lateinit var processor: MainProcessor
    private lateinit var reducer: MainReducer

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        processor = MainProcessor(repository)
        reducer = MainReducer()
    }

    @Test
    fun `노트_목록_관찰_액션을_보내면_상태가_업데이트되어야_한다`() {
        val notes = listOf(
            Note(id = 1, title = "Title", content = "Content")
        )

        testViewModel(
            createViewModel = { MainViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock {
                    coEvery { repository.getAllNotes() } returns flow {
                        emit(notes)
                    }
                }
            }

            whenBlock {
                action {
                    sendAction(MainContract.Action.Stream.ObserveNotes)
                }
            }

            thenBlock {
                // 데이터 로드 완료
                lastState {
                    assertEquals(notes, current.notes)
                    assertFalse(current.isLoading)
                }
            }
        }
    }

    @Test
    fun `그리드_토글_액션을_보내면_isGrid_상태가_변경되어야_한다`() {
        testViewModel(
            createViewModel = { MainViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action {
                    sendAction(MainContract.Action.ToggleListMode)
                }
            }

            thenBlock {
                state {
                    assertEquals(!initial.isGrid, current.isGrid)
                }
            }
        }
    }

    @Test
    fun `그리드_토글_액션을_두번_보내면_isGrid가_원래대로_돌아와야_한다`() {
        testViewModel(
            createViewModel = { MainViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action {
                    sendAction(MainContract.Action.ToggleListMode)
                    sendAction(MainContract.Action.ToggleListMode)
                }
            }

            thenBlock {
                // 첫 번째 토글
                state {
                    assertTrue(current.isGrid)
                }

                // 두 번째 토글
                state {
                    assertFalse(current.isGrid)
                }
            }
        }
    }

    @Test
    fun `노트_삭제_액션을_보내면_레포지토리의_deleteNote가_호출되어야_한다`() {
        val note = Note(id = 1, title = "Delete Me", content = "Content")

        testViewModel(
            createViewModel = { MainViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock {
                    coEvery { repository.deleteNote(note) } returns Unit
                }
            }

            whenBlock {
                action {
                    sendAction(MainContract.Action.DeleteNote(note))
                }
            }

            thenBlock {
                noStateChange()
            }
        }

        coVerify(exactly = 1) { repository.deleteNote(note) }
    }

    @Test
    fun `상세_화면_이동_액션을_보내면_NavigateToDetail_이벤트가_발생해야_한다`() {
        val noteId = 1L

        testViewModel(
            createViewModel = { MainViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action {
                    sendAction(MainContract.Action.NavigateToDetail(noteId))
                }
            }

            thenBlock {
                event {
                    assertEquals(MainContract.Event.NavigateToDetail(noteId), this)
                }
            }
        }
    }

    @Test
    fun `노트_검색_액션을_보내면_검색_결과가_업데이트되어야_한다`() {
        val query = "Search Query"
        val searchResults = listOf(Note(id = 2, title = "Search Result", content = "Content"))

        testViewModel(
            createViewModel = { MainViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock {
                    coEvery { repository.searchNotes(query) } returns flow {
                        emit(searchResults)
                    }
                }
            }

            whenBlock {
                action {
                    sendAction(MainContract.Action.SearchNote(query))
                }
            }

            thenBlock {
                lastState {
                    assertFalse(current.isLoading)
                    assertEquals(query, current.searchQuery)
                    assertEquals(searchResults, current.notes)
                }
            }
        }
    }

    @Test
    fun `초기_상태가_올바른지_확인한다`() {
        testViewModel(
            createViewModel = { MainViewModel(processor, reducer) }
        ) {
            givenBlock {
                mock { }
            }

            whenBlock {
                action { }
            }

            thenBlock {
                initialState {
                    assertFalse(isGrid)
                    assertEquals(emptyList<Note>(), notes)
                    assertFalse(isLoading)
                    assertEquals("", searchQuery)
                }
            }
        }
    }
}