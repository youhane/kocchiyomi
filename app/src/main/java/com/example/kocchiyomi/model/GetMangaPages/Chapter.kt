package com.example.kocchiyomi.model.GetMangaPages


import com.google.gson.annotations.SerializedName

data class Chapter(
    @SerializedName("data")
    val `data`: List<String>,
    @SerializedName("dataSaver")
    val dataSaver: List<String>,
    @SerializedName("hash")
    val hash: String
)