package com.bor96dev.data

import retrofit2.http.GET

interface ApiService {
    @GET("uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&e\n" +
            "xport=download")
    suspend fun getCourses(): CoursesResponse
}