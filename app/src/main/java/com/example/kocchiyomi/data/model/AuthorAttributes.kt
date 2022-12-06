package com.example.kocchiyomi.data.model

import com.google.gson.annotations.SerializedName

data class AuthorAttributes(
    @SerializedName("createdAt") val createdAt: String?,
    val name: String?,
    @SerializedName("updatedAt") val updatedAt: String?,
    val version: Int?,
)