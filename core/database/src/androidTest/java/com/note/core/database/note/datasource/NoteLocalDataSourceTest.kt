package com.note.core.database.note.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.note.core.database.NoteDatabase
import com.note.core.database.entity.NoteEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteLocalDataSourceTest {

    private lateinit var database: NoteDatabase
    private lateinit var localDataSource: NoteLocalDataSource

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        localDataSource = NoteLocalDataSourceImpl(database.noteDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `노트_저장_및_조회_테스트`() = runTest {
        // Given
        val note = NoteEntity(id = 1, title = "Title", content = "Content")

        // When
        localDataSource.insertNote(note)
        val loaded = localDataSource.getNoteById(1)

        // Then
        assertEquals(note.title, loaded?.title)
        assertEquals(note.content, loaded?.content)
    }

    @Test
    fun `노트_삭제_테스트`() = runTest {
        // Given
        val note = NoteEntity(id = 1, title = "Title", content = "Content")
        localDataSource.insertNote(note)

        // When
        localDataSource.deleteNote(note)
        val loaded = localDataSource.getNoteById(1)

        // Then
        assertEquals(null, loaded)
    }

    @Test
    fun `모든_노트_조회_테스트`() = runTest {
        // Given
        val note1 = NoteEntity(id = 1, title = "Title 1", content = "Content 1")
        val note2 = NoteEntity(id = 2, title = "Title 2", content = "Content 2")
        localDataSource.insertNote(note1)
        localDataSource.insertNote(note2)

        // When
        val notes = localDataSource.getAllNotes().first()

        // Then
        assertEquals(2, notes.size)
    }
}
