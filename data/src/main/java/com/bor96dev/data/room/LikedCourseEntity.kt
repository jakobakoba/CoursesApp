package com.bor96dev.data.room

import androidx.room.Entity

@Entity(tableName = "courses")
data class LikedCourseEntity (
    val id: Int
)