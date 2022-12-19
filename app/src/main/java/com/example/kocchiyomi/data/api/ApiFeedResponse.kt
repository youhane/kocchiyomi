package com.example.kocchiyomi.data.api

import com.example.kocchiyomi.data.model.Manga
import com.google.gson.annotations.SerializedName

data class ApiFeedResponse(
    val result: String?= null,
    val response: String?= null,
    val data: List<Manga>?= emptyList(),
    val limit: Int?= null,
    val offset: Int?= null,
    val total: Int?= null,
)
