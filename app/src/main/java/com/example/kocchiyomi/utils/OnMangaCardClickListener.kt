package com.example.kocchiyomi.utils

import com.example.kocchiyomi.data.model.Manga

interface OnMangaCardClickListener {
    fun OnMangaCardClick(data: Manga)
}