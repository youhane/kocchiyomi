package com.example.kocchiyomi.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ChapterAttributes(
    val volume: String,
    val chapter: String?,
    val title: String?="No Chapter Title",
    @SerializedName("translatedLanguage") val translatedLanguage: String?,
    @SerializedName("publishAt") val publishAt: Date?,
    val pages: Int,
    val version: Int
)