package com.example.kocchiyomi.data.firebase.model

import com.example.kocchiyomi.data.entity.MangaEntity
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

@Suppress("ClassName")
@IgnoreExtraProperties
data class _LibraryManga(
    @get:PropertyName("id") @set:PropertyName("id")
    var id: String,
) {
    fun toDomain(): MangaEntity {
        return MangaEntity(
            id = id,
        )
    }

    @Suppress("unused")
    constructor() : this(
        id = "",
    )
}
