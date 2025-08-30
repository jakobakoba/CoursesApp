package com.bor96dev.data

import com.bor96dev.domain.CourseRepository
import jakarta.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val courseDao: CourseDao
): CourseRepository {
}