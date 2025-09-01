package com.bor96dev.domain.usecase

import com.bor96dev.domain.CourseRepository

class ToggleFavoriteUseCase (
    private val repository: CourseRepository
) {
    suspend operator fun invoke(courseId: Int) {
        repository.updateCourseLike(courseId, true) // Toggle logic is handled in the repository
    }
}