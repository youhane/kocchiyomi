package com.example.kocchiyomi.model.GetMangaPages


import com.google.gson.annotations.SerializedName

data class MangaPages(
    @SerializedName("baseUrl")
    val baseUrl: String,
    @SerializedName("chapter")
    val chapter: Chapter,
    @SerializedName("result")
    val result: String
)