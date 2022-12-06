package com.example.kocchiyomi.data.model

import com.google.gson.annotations.SerializedName

data class RelationshipAttributes(
    val name: String? = null,
    val description: String? = null,
    @SerializedName("fileName") val fileName: String? = null,
    val locale: String? = null,
    val version: Int? = null,
    val volume: String? = null,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedAt") val updatedAt: String?,
)