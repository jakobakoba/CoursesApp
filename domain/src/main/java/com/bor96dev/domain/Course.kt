package com.bor96dev.domain

data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    var hasLike: Boolean,
    val publishDate: String
)
