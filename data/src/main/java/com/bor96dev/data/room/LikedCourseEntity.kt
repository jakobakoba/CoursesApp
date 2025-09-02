package com.bor96dev.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class LikedCourseEntity (
    @PrimaryKey
    val id: Int
)