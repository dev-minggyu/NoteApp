package com.note.core.repository.note

import com.note.core.database.entity.NoteEntity
import com.note.core.database.note.datasource.NoteLocalDataSource
import com.note.domain.model.Note
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteRepositoryImplTest {

    private lateinit var noteRepository: NoteRepositoryImpl
    private lateinit var noteLocalDataSource: NoteLocalDataSource

    @Before
    fun setUp() {
        noteLocalDataSource = mockk()
        noteRepository = NoteRepositoryImpl(noteLocalDataSource)
    }

    @Test
    fun `모든_노트를_가져올때_Entity가_Domain모델로_매핑되어야_한다`() = runTest {
        // Given
        val noteEntities = listOf(
            NoteEntity(id = 1, title = "Title 1", content = "Content 1"),
            NoteEntity(id = 2, title = "Title 2", content = "Content 2")
        )
        every { noteLocalDataSource.getAllNotes() } returns flowOf(noteEntities)

        // When
        val result = noteRepository.getAllNotes().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Title 1", result[0].title)
        assertEquals("Title 2", result[1].title)
        verify { noteLocalDataSource.getAllNotes() }
    }

    @Test
    fun `ID로_노트_조회시_매핑된_Domain모델을_반환해야_한다`() = runTest {
        // Given
        val noteEntity = NoteEntity(id = 1, title = "Title", content = "Content")
        coEvery { noteLocalDataSource.getNoteById(1) } returns noteEntity

        // When
        val result = noteRepository.getNoteById(1)

        // Then
        assertEquals("Title", result?.title)
        coVerify { noteLocalDataSource.getNoteById(1) }
    }

    @Test
    fun `존재하지_않는_ID로_조회시_null을_반환해야_한다`() = runTest {
        // Given
        coEvery { noteLocalDataSource.getNoteById(1) } returns null

        // When
        val result = noteRepository.getNoteById(1)

        // Then
        assertEquals(null, result)
        coVerify { noteLocalDataSource.getNoteById(1) }
    }

    @Test
    fun `노트_추가시_DataSource에게_위임해야_한다`() = runTest {
        // Given
        val note = Note(id = 0, title = "Title", content = "Content")
        coEvery { noteLocalDataSource.insertNote(any()) } returns 1L

        // When
        val result = noteRepository.insertNote(note)

        // Then
        assertEquals(1L, result)
        coVerify { noteLocalDataSource.insertNote(any()) }
    }

    @Test
    fun `노트_업데이트시_DataSource에게_위임해야_한다`() = runTest {
        // Given
        val note = Note(id = 1, title = "Title", content = "Content")
        coEvery { noteLocalDataSource.updateNote(any()) } returns Unit

        // When
        noteRepository.updateNote(note)

        // Then
        coVerify { noteLocalDataSource.updateNote(any()) }
    }

    @Test
    fun `노트_삭제시_DataSource에게_위임해야_한다`() = runTest {
        // Given
        val note = Note(id = 1, title = "Title", content = "Content")
        coEvery { noteLocalDataSource.deleteNote(any()) } returns Unit

        // When
        noteRepository.deleteNote(note)

        // Then
        coVerify { noteLocalDataSource.deleteNote(any()) }
    }

    @Test
    fun `노트_검색시_Entity가_Domain모델로_매핑되어야_한다`() = runTest {
        // Given
        val query = "Title"
        val noteEntities = listOf(
            NoteEntity(id = 1, title = "Title 1", content = "Content 1")
        )
        every { noteLocalDataSource.searchNotes(query) } returns flowOf(noteEntities)

        // When
        val result = noteRepository.searchNotes(query).first()

        // Then
        assertEquals(1, result.size)
        assertEquals("Title 1", result[0].title)
        verify { noteLocalDataSource.searchNotes(query) }
    }

    @Test
    fun `알람이_설정된_노트_조회시_Entity가_Domain모델로_매핑되어야_한다`() = runTest {
        // Given
        val noteEntities = listOf(
            NoteEntity(id = 1, title = "Title 1", content = "Content 1", isAlarmEnabled = true)
        )
        every { noteLocalDataSource.getEnabledAlarms() } returns flowOf(noteEntities)

        // When
        val result = noteRepository.getEnabledAlarms().first()

        // Then
        assertEquals(1, result.size)
        assertEquals(true, result[0].isAlarmEnabled)
        verify { noteLocalDataSource.getEnabledAlarms() }
    }
}
