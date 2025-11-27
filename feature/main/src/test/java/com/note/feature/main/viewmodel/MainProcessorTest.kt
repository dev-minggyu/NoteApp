package com.note.feature.main.viewmodel

import com.note.domain.model.Note
import com.note.domain.repository.NoteRepository
import com.note.feature.main.MainContract
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MainProcessorTest {

    private lateinit var repository: NoteRepository
    private lateinit var processor: MainProcessor

    @Before
    fun setUp() {
        repository = mockk()
        processor = MainProcessor(repository)
    }

    @Test
    fun `노트_목록_관찰시_로딩과_데이터를_방출해야_한다`() = runTest {
        // Given
        val notes = listOf(
            Note(id = 1, title = "Title", content = "Content")
        )
        coEvery { repository.getAllNotes() } returns flowOf(notes)

        // When
        val mutations = processor.process(MainContract.Action.Stream.ObserveNotes).toList()

        // Then
        assertTrue(mutations[0] is MainContract.Mutation.ShowProgress)
        assertTrue(mutations[1] is MainContract.Mutation.NoteLoaded)
        assertEquals(notes, (mutations[1] as MainContract.Mutation.NoteLoaded).notes)
    }

    @Test
    fun `노트_삭제시_성공하면_NoteDeleted를_방출해야_한다`() = runTest {
        // Given
        val note = Note(id = 1, title = "Title", content = "Content")
        coEvery { repository.deleteNote(note) } returns Unit

        // When
        val mutations = processor.process(MainContract.Action.DeleteNote(note)).toList()

        // Then
        assertTrue(mutations[0] is MainContract.Mutation.NoteDeleted)
        assertEquals(note, (mutations[0] as MainContract.Mutation.NoteDeleted).note)
    }

    @Test
    fun `노트_검색시_검색어_업데이트와_로딩_그리고_결과를_방출해야_한다`() = runTest {
        // Given
        val query = "Title"
        val notes = listOf(
            Note(id = 1, title = "Title", content = "Content")
        )
        coEvery { repository.searchNotes(query) } returns flowOf(notes)

        // When
        val mutations = processor.process(MainContract.Action.SearchNote(query)).toList()

        // Then
        // 1. UpdateSearchQuery
        assertTrue(mutations[0] is MainContract.Mutation.UpdateSearchQuery)
        assertEquals(query, (mutations[0] as MainContract.Mutation.UpdateSearchQuery).searchQuery)
        
        // 2. ShowProgress
        assertTrue(mutations[1] is MainContract.Mutation.ShowProgress)
        
        // 3. SearchResult
        assertTrue(mutations[2] is MainContract.Mutation.SearchResult)
        assertEquals(notes, (mutations[2] as MainContract.Mutation.SearchResult).notes)
    }
}
