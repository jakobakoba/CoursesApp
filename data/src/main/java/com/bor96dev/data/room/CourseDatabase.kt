package com.bor96dev.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LikedCourseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CourseDatabase: RoomDatabase() {
    abstract fun courseDao(): CourseDao
}