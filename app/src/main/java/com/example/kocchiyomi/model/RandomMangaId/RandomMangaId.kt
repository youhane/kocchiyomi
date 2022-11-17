package com.example.kocchiyomi.model.RandomMangaId


import com.google.gson.annotations.SerializedName

data class RandomMangaId(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("response")
    val response: String,
    @SerializedName("result")
    val result: String
)