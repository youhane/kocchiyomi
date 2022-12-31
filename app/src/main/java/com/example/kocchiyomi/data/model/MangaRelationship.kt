package com.example.kocchiyomi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MangaRelationship(
    val id: String? = null,
    val type: String? = null,
    val attributes: RelationshipAttributes? = null,
    val related: String? = null
): Parcelable