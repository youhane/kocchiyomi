package com.example.kocchiyomi.database
import com.example.kocchiyomi.data.api.ApiLibraryResponse
import com.example.kocchiyomi.data.model.Manga

interface MangaFirestore {
    fun getAll(): List<ApiLibraryResponse>
//
//    fun getById(id: String): MangaEntity?

    fun save(manga: Manga)

    fun delete(id: String)
}