package com.example.kocchiyomi.data.api

import com.example.kocchiyomi.data.model.Manga

data class ApiFeedResponse(
    val result: String?= null,
    val response: String?= null,
    val data: List<Manga>?= emptyList(),
    val limit: Int?= null,
    val offset: Int?= null,
    val total: Int?= null,
)
