package com.example.kocchiyomi.model.RandomMangaId


import com.google.gson.annotations.SerializedName

data class Relationship(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String
)