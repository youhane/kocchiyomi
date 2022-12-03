package com.example.kocchiyomi.data.model

data class Chapter(
    val id: String,
    val attributes: ChapterAttributes,
    val relationships: List<ScanlationGroupRelationship>
)
