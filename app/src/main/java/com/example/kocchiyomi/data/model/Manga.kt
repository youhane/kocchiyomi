package com.example.kocchiyomi.data.model

import java.io.Serializable

data class Manga(
    val attributes: MangaAttributes,
    val id: String,
    val relationships: List<MangaRelationship>,
    val type: String
): Serializable