package com.bor96dev.data

import com.bor96dev.data.room.CourseDao
import com.bor96dev.data.room.LikedCourseEntity
import com.bor96dev.domain.Course
import com.bor96dev.domain.CourseRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class CourseRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val courseDao: CourseDao
): CourseRepository {
    override fun getCourses(): Flow<List<Course>> {
        val remoteCoursesFlow = flow {
            emit(apiService.getCourses().courses)
        }
        val likedCoursesIdsFlow = courseDao.getLikedCourseIds()

        return remoteCoursesFlow.combine(likedCoursesIdsFlow) { remoteCourses, likedIds ->
            remoteCourses.map { courseDto ->
                val isLiked = likedIds.contains(courseDto.id)
                courseDto.toDomain(isLiked = isLiked)
            }
        }
    }

    override suspend fun updateCourseLike(courseId: Int, hasLike: Boolean) {
        if (hasLike) {
            courseDao.likeCourse(LikedCourseEntity(id = courseId))
        } else {
            courseDao.unlikeCourse(courseId)
        }
    }

}