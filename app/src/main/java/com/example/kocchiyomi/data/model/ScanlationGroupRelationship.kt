package com.example.kocchiyomi.data.model

import androidx.room.Embedded

data class ScanlationGroupRelationship(
    val id: String? = null,
    val type: String? = null,
    @Embedded val attributes: ScanlationGroupAttribute? = null
)