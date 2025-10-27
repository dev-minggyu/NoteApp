package com.note.core.repository.exam

import com.note.core.database.datasource.exam.ExamLocalDataSource
import com.note.core.database.model.ExamEntity
import com.note.core.network.datasource.exam.ExamRemoteDataSource
import com.note.core.network.model.ExamResponse
import com.note.domain.model.Exam
import com.note.domain.repository.ExamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExamRepositoryImpl(
    private val remoteDataSource: ExamRemoteDataSource,
    private val localDataSource: ExamLocalDataSource,
) : ExamRepository {

    override fun getExams(): Flow<List<Exam>> {
        return localDataSource.getExams().map { examEntities ->
            examEntities.map { it.toDomain() }
        }
    }

    override fun getExamById(id: String): Flow<Exam?> {
        return localDataSource.getExamById(id).map { it?.toDomain() }
    }

    override suspend fun refreshExams() {
        val remoteExams = remoteDataSource.getExams().map { examResponses ->
            examResponses.map { it.toEntity() }
        }
        remoteExams.collect { examEntities ->
            localDataSource.saveExams(examEntities)
        }
    }

    override suspend fun refreshExam(id: String) {
        val remoteExam = remoteDataSource.getExamById(id).map { it.toEntity() }
        remoteExam.collect { examEntity ->
            localDataSource.saveExam(examEntity)
        }
    }

    private fun ExamEntity.toDomain(): Exam {
        return Exam(id = this.exam)
    }

    private fun ExamResponse.toEntity(): ExamEntity {
        return ExamEntity(exam = this.exam)
    }
}