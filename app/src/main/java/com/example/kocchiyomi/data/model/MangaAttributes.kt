package com.example.kocchiyomi.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MangaAttributes(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    val description: Description? = null,
    val status: String? = null,
    val tags: List<Tag>? = null,
    val title: Title? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    val version: Int? = null,
    val year: Int? = null
): Parcelable