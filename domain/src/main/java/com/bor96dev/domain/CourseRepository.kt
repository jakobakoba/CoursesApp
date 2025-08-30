package com.bor96dev.domain

import kotlinx.coroutines.flow.Flow


interface CourseRepository {
    fun getCourses(): Flow<List<Course>>
    suspend fun updateCourseLike(courseId: Int, hasLike: Boolean)
}