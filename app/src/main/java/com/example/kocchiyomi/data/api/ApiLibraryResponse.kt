package com.example.kocchiyomi.data.api

import com.example.kocchiyomi.data.model.Manga
import com.google.gson.annotations.SerializedName

data class ApiLibraryResponse(
    val result: String,
    val response: String,
    val data: List<Manga>,
    val limit: Int,
    val offset: Int,
    val total: Int,
)
