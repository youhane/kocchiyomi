package com.example.kocchiyomi.data.model

import com.google.gson.annotations.SerializedName

data class AuthorAttributes(
    @SerializedName("createdAt") val createdAt: String? = null,
    val name: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null,
    val version: Int? = null,
)