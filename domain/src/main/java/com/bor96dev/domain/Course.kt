package com.bor96dev.domain

data class Course(
    val id: String,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val startDate: String,
    var hasLike: Boolean,
    val publishDate: String
)
