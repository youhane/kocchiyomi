package com.example.kocchiyomi.network

import com.example.kocchiyomi.model.RandomMangaId.RandomMangaId
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("random")
    fun getRandomManga(): Call<RandomMangaId>

}