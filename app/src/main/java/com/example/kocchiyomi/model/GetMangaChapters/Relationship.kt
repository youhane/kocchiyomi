package com.example.kocchiyomi.model.GetMangaChapters


import com.google.gson.annotations.SerializedName

data class Relationship(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String
)