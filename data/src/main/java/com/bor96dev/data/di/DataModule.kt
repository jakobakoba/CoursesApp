package com.bor96dev.data.di

import android.content.Context
import androidx.room.Room
import com.bor96dev.data.ApiService
import com.bor96dev.data.CourseRepositoryImpl
import com.bor96dev.data.room.CourseDao
import com.bor96dev.data.room.CourseDatabase
import com.bor96dev.domain.CourseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl("https://drive.google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CourseDatabase = Room.databaseBuilder(
        context,
        CourseDatabase::class.java,
        "courses_db"
    ).build()

    @Provides
    @Singleton
    fun provideCourseDao(database: CourseDatabase): CourseDao = database.courseDao()

    @Provides
    @Singleton
    fun provideCourseRepository(
        apiService: ApiService,
        courseDao: CourseDao
    ): CourseRepository = CourseRepositoryImpl(
        apiService,
        courseDao
    )
}