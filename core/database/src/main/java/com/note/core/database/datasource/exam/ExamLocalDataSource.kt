package com.note.core.database.datasource.exam

import com.note.core.database.model.ExamEntity
import kotlinx.coroutines.flow.Flow

interface ExamLocalDataSource {
    fun getExams(): Flow<List<ExamEntity>>
    
    fun getExamById(id: String): Flow<ExamEntity?>
    
    suspend fun saveExam(exam: ExamEntity)
    
    suspend fun saveExams(exams: List<ExamEntity>)
    
    suspend fun deleteExamById(id: String)
    
    suspend fun deleteAllExams()
} 