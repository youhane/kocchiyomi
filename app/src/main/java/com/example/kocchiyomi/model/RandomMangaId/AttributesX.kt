package com.example.kocchiyomi.model.RandomMangaId


import com.google.gson.annotations.SerializedName

data class AttributesX(
    @SerializedName("description")
    val description: List<Any>,
    @SerializedName("group")
    val group: String,
    @SerializedName("name")
    val name: Name,
    @SerializedName("version")
    val version: Int
)