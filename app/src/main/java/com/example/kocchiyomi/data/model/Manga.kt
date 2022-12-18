package com.example.kocchiyomi.data.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class Manga(
    val attributes: MangaAttributes? = null,
    val id: String? = null,
    val relationships: List<MangaRelationship>? = null,
    val type: String? = null,
    var timestamp: Timestamp? = null
): Serializable