package com.example.kocchiyomi.data.model

data class CoverRelationship(
    val id: String,
    val type: String,
    val attributes: CoverAttribute?,
    val related: String?
)