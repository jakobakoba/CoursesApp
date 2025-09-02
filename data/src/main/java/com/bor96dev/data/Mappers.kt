package com.bor96dev.data

import com.bor96dev.data.dto.CourseDto
import com.bor96dev.domain.Course

fun CourseDto.toDomain(isLiked: Boolean): Course {
    return Course(
        id = this.id,
        title = this.title,
        text = this.text,
        price = this.price,
        rate = this.rate,
        hasLike = isLiked,
        publishDate = this.publishDate
    )
}