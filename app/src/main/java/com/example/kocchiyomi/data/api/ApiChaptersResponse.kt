package com.example.kocchiyomi.data.api

import com.example.kocchiyomi.data.model.Chapter

data class ApiChaptersResponse(
    val result: String,
    val data: List<Chapter>,
    val limit: Int,
    val total: Int
)