package com.bor96dev.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT id FROM courses")
    fun getLikedCourseIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun likeCourse(likedCourse: LikedCourseEntity)

    @Query("DELETE FROM courses WHERE id = :courseId")
    suspend fun unlikeCourse(courseId: Int)
}