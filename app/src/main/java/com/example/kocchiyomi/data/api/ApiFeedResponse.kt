package com.example.kocchiyomi.data.api

import com.example.kocchiyomi.data.model.Manga
import com.google.gson.annotations.SerializedName

data class ApiFeedResponse(
    val result: String,
    val response: String,
    @SerializedName("data") val mangaList: List<Manga>,
    val limit: Int,
    val offset: Int,
    val total: Int,
)
