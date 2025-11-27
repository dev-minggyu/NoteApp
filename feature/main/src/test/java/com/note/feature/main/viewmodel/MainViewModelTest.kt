package com.note.feature.main.viewmodel

import app.cash.turbine.test
import com.note.domain.model.Note
import com.note.domain.repository.NoteRepository
import com.note.feature.main.MainContract
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: NoteRepository
    private lateinit var processor: MainProcessor
    private lateinit var reducer: MainReducer
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        repository = mockk()
        processor = MainProcessor(repository)
        reducer = MainReducer()
    }

    @Test
    fun `노트_목록_관찰_액션을_보내면_상태가_업데이트되어야_한다`() = runTest {
        // Given
        val notes = listOf(
            Note(id = 1, title = "Title", content = "Content")
        )
        coEvery { repository.getAllNotes() } returns flow {
            delay(10)
            emit(notes)
        }
        
        viewModel = MainViewModel(processor, reducer)

        // When & Then
        viewModel.uiState.test {
            // 1. Initial State
            awaitItem() 
            
            // Send Action explicitly (since streamIntents are now lazy)
            viewModel.sendAction(MainContract.Action.Stream.ObserveNotes)
            
            // 2. Loading State (ShowProgress)
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)
            
            // 3. Loaded State (NoteLoaded)
            val loadedState = awaitItem()
            assertEquals(notes, loadedState.notes)
            assertEquals(false, loadedState.isLoading)
        }
    }

    @Test
    fun `그리드_토글_액션을_보내면_isGrid_상태가_변경되어야_한다`() = runTest {
        // Given
        viewModel = MainViewModel(processor, reducer)
        
        viewModel.uiState.test {
            val initialState = awaitItem() // Initial
            
            // When
            viewModel.sendAction(MainContract.Action.ToggleListMode)

            // Then
            val toggledState = awaitItem()
            assertEquals(!initialState.isGrid, toggledState.isGrid)
        }
    }
}
