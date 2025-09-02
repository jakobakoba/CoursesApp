package com.bor96dev.domain.usecase

import com.bor96dev.domain.CourseRepository
import javax.inject.Inject


class ToggleFavoriteUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(courseId: Int, isFavorite: Boolean) {
        repository.updateCourseLike(courseId, isFavorite)
    }
}