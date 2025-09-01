package com.bor96dev.coursesapp.di

import com.bor96dev.domain.usecase.GetCoursesUseCase
import com.bor96dev.domain.CourseRepository
import com.bor96dev.domain.usecase.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideGetCoursesUseCase(
        repository: CourseRepository
    ): GetCoursesUseCase = GetCoursesUseCase(repository)

    @Provides
    @Singleton
    fun provideToggleFavoriteUseCase(
        repository: CourseRepository
    ): ToggleFavoriteUseCase = ToggleFavoriteUseCase(repository)
}
