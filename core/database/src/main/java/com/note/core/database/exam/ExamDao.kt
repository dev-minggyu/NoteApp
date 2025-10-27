package com.note.core.database.exam

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.note.core.database.model.ExamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {
    @Query("SELECT * FROM exam")
    fun getExams(): Flow<List<ExamEntity>>
    
    @Query("SELECT * FROM exam WHERE exam = :id")
    fun getExamById(id: String): Flow<ExamEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExam(exam: ExamEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExams(exams: List<ExamEntity>)
    
    @Query("DELETE FROM exam WHERE exam = :id")
    suspend fun deleteExamById(id: String)
    
    @Query("DELETE FROM exam")
    suspend fun deleteAllExams()
} 