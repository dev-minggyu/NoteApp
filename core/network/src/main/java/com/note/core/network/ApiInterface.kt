package com.note.core.network

import com.note.core.network.model.ExamResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {
    companion object {
        const val BASE_URL = "https://api.example.com/"
    }
    
    @GET("exams")
    suspend fun getExams(): List<ExamResponse>
    
    @GET("exams/{id}")
    suspend fun getExamById(@Path("id") id: String): ExamResponse
    
    @POST("exams")
    suspend fun createExam(@Body exam: ExamResponse): ExamResponse
    
    @PUT("exams/{id}")
    suspend fun updateExam(@Path("id") id: String, @Body exam: ExamResponse): ExamResponse
    
    @DELETE("exams/{id}")
    suspend fun deleteExam(@Path("id") id: String): Boolean
} 