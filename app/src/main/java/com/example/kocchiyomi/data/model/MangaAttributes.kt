package com.example.kocchiyomi.data.model

import com.google.gson.annotations.SerializedName

data class MangaAttributes(
    @SerializedName("createdAt") val createdAt: String?,
    val description: Description,
    val status: String?,
    val tags: List<Tag>,
    val title: Title,
    @SerializedName("updatedAt") val updatedAt: String?,
    val version: Int?,
    val year: Int?
)