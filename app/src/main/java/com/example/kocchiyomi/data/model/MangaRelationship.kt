package com.example.kocchiyomi.data.model

data class MangaRelationship(
    val id: String,
    val type: String,
    val attributes: RelationshipAttributes?,
    val related: String?
)