package com.example.kocchiyomi.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kocchiyomi.data.model.ChapterAttributes
import com.example.kocchiyomi.data.model.ScanlationGroupRelationship

@Entity(tableName = "chapter")
data class ChapterEntity (
    @NonNull @PrimaryKey val id: String,
    @Embedded val attributes: ChapterAttributes,

//    val relationships: List<ScanlationGroupRelationship>
)