package com.example.kocchiyomi.data.api

import com.example.kocchiyomi.data.model.Manga

data class ApiMangaResponse(
    val data: Manga,
    val response: String,
    val result: String
)