package com.example.kocchiyomi.data.api

data class Chapter(
    val id: String,
    val attributes: ChapterAttributes,
    val relationships: List<ScanlationGroupRelationship>
)
