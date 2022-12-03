package com.example.kocchiyomi.data.model

import java.io.Serializable

data class Manga(
    val attributes: MangaAttributes,
    val id: String,
    val relationships: List<CoverRelationship>,
    val type: String
): Serializable