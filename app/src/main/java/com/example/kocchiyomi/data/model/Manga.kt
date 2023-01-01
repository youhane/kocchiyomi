package com.example.kocchiyomi.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.Serializable

@Parcelize
data class Manga(
    @SerializedName("attributes")
    val attributes: MangaAttributes? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("relationships")
    val relationships: List<MangaRelationship>? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("timestamp")
    var timestamp: Timestamp? = null
): Parcelable