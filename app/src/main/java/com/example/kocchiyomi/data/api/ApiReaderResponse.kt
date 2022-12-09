package com.example.kocchiyomi.data.api

import com.example.kocchiyomi.data.model.ChapterFiles
import com.google.gson.annotations.SerializedName

data class ApiReaderResponse (
    val result: String,
    @SerializedName("baseUrl") val baseUrl: String,
    @SerializedName("chapter") val chapterFiles: ChapterFiles
)