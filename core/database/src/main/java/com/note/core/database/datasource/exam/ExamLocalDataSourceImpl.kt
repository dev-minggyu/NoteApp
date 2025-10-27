package com.note.core.database.datasource.exam

import com.note.core.database.exam.ExamDao
import com.note.core.database.model.ExamEntity
import kotlinx.coroutines.flow.Flow

class ExamLocalDataSourceImpl(
    private val examDao: ExamDao
) : ExamLocalDataSource {
    
    override fun getExams(): Flow<List<ExamEntity>> {
        return examDao.getExams()
    }
    
    override fun getExamById(id: String): Flow<ExamEntity?> {
        return examDao.getExamById(id)
    }
    
    override suspend fun saveExam(exam: ExamEntity) {
        examDao.insertExam(exam)
    }
    
    override suspend fun saveExams(exams: List<ExamEntity>) {
        examDao.insertExams(exams)
    }
    
    override suspend fun deleteExamById(id: String) {
        examDao.deleteExamById(id)
    }
    
    override suspend fun deleteAllExams() {
        examDao.deleteAllExams()
    }
}