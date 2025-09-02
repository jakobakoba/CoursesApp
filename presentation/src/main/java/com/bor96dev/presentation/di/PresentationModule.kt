package com.bor96dev.presentation.di

import com.bor96dev.domain.usecase.GetCoursesUseCase
import com.bor96dev.domain.usecase.ToggleFavoriteUseCase
import com.bor96dev.presentation.courses.CoursesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule {

    @Provides
    @ViewModelScoped
    fun provideCoursesViewModel (
        getCoursesUseCase: GetCoursesUseCase,
        toggleFavoriteUseCase: ToggleFavoriteUseCase
    ): CoursesViewModel {
        return CoursesViewModel(getCoursesUseCase, toggleFavoriteUseCase)
    }
}