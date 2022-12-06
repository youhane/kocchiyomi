package com.example.kocchiyomi.data.model

data class AuthorRelationship(
    val id: String,
    val type: String,
    val attributes: AuthorAttributes?,
)