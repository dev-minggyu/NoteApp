package com.note.core.network.datasource.exam

import com.note.core.network.ApiInterface
import com.note.core.network.model.ExamResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExamRemoteDataSourceImpl(private val apiInterface: ApiInterface) : ExamRemoteDataSource {
    
    override suspend fun getExams(): Flow<List<ExamResponse>> = flow {
        emit(listOf(ExamResponse("exam1"), ExamResponse("exam2")))
    }
    
    override suspend fun getExamById(id: String): Flow<ExamResponse> = flow {
        emit(ExamResponse(id))
    }
    
    override suspend fun createExam(exam: ExamResponse): Flow<ExamResponse> = flow {
        emit(exam)
    }
    
    override suspend fun updateExam(id: String, exam: ExamResponse): Flow<ExamResponse> = flow {
        emit(exam)
    }
    
    override suspend fun deleteExam(id: String): Flow<Boolean> = flow {
        emit(true)
    }
} 