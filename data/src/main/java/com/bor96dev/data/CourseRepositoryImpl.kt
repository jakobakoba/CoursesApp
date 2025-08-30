package com.bor96dev.data

import jakarta.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val courseDao: CourseDao
): CourseRepository {
}