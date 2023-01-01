package com.example.kocchiyomi.data.model

data class Chapter(
    val id: String?= null,
    val attributes: ChapterAttributes?= null,
    val relationships: List<ScanlationGroupRelationship>?= null
)
