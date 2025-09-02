package com.bor96dev.domain.usecase

import com.bor96dev.domain.Course
import com.bor96dev.domain.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    operator fun invoke(): Flow<List<Course>> = repository.getCourses()
}