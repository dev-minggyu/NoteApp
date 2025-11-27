package com.note.feature.main.viewmodel

import com.note.domain.model.Note
import com.note.feature.main.MainContract
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MainReducerTest {

    private lateinit var reducer: MainReducer

    @Before
    fun setUp() {
        reducer = MainReducer()
    }

    @Test
    fun `ShowProgress_뮤테이션은_로딩상태를_true로_변경해야_한다`() {
        // Given
        val currentState = MainContract.State(isLoading = false)
        val mutation = MainContract.Mutation.ShowProgress

        // When
        val result = reducer.reduce(currentState, mutation)

        // Then
        assertTrue(result.newState.isLoading)
    }

    @Test
    fun `NoteLoaded_뮤테이션은_노트목록을_업데이트하고_로딩을_false로_변경해야_한다`() {
        // Given
        val currentState = MainContract.State(isLoading = true)
        val notes = persistentListOf(
            Note(id = 1, title = "Title", content = "Content")
        )
        val mutation = MainContract.Mutation.NoteLoaded(notes)

        // When
        val result = reducer.reduce(currentState, mutation)

        // Then
        assertFalse(result.newState.isLoading)
        assertEquals(notes, result.newState.notes)
    }

    @Test
    fun `ToggleView_뮤테이션은_그리드모드를_토글해야_한다`() {
        // Given
        val currentState = MainContract.State(isGrid = true)
        val mutation = MainContract.Mutation.ToggleView

        // When
        val result = reducer.reduce(currentState, mutation)

        // Then
        assertFalse(result.newState.isGrid)
    }

    @Test
    fun `UpdateSearchQuery_뮤테이션은_검색어를_업데이트해야_한다`() {
        // Given
        val currentState = MainContract.State(searchQuery = "")
        val query = "New Query"
        val mutation = MainContract.Mutation.UpdateSearchQuery(query)

        // When
        val result = reducer.reduce(currentState, mutation)

        // Then
        assertEquals(query, result.newState.searchQuery)
    }
}
