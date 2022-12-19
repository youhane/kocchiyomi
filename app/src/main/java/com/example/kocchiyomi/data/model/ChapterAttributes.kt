package com.example.kocchiyomi.data.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.util.Date

data class ChapterAttributes(
    val volume: String? = null,
    val chapter: String? = null,
    val title: String?="No Chapter Title",
    @SerializedName("translatedLanguage") val translatedLanguage: String? = null,
    @Embedded @SerializedName("publishAt") val publishAt: Date? = null,
    @Embedded @SerializedName("createdAt") val createdAt: Date? = null,
    val pages: Int? = null,
    val version: Int? = null
)