package com.example.kocchiyomi.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Manga(
    val attributes: MangaAttributes? = null,
    val id: String? = null,
    val relationships: List<MangaRelationship>? = null,
    val type: String? = null,
    var timestamp: Timestamp? = null
): Parcelable