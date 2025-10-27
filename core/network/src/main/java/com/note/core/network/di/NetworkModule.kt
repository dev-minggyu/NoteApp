package com.note.core.network.di

import com.note.core.network.ApiInterface
import com.note.core.network.datasource.exam.ExamRemoteDataSource
import com.note.core.network.datasource.exam.ExamRemoteDataSourceImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val networkModule = module {
    single<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(get())
            .build()
    }

    single<ApiInterface> {
        get<Retrofit>().create(ApiInterface::class.java)
    }

    single<ExamRemoteDataSource> {
        ExamRemoteDataSourceImpl(get())
    }
}