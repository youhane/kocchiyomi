package com.example.kocchiyomi.data.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.util.Date

data class ChapterAttributes(
    val volume: String? = null,
    val chapter: String? = "0",
    val title: String?="No Chapter Title",
    @SerializedName("translatedLanguage") val translatedLanguage: String? = null,
    @SerializedName("publishAt") val publishAt: Date? = null,
    @SerializedName("createdAt") val createdAt: Date? = null,
    val pages: Int? = null,
    val version: Int? = null
)