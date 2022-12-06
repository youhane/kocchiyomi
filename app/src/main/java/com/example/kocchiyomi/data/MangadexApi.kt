package com.example.kocchiyomi.data

import com.example.kocchiyomi.data.api.ApiChaptersResponse
import com.example.kocchiyomi.data.api.ApiFeedResponse
import com.example.kocchiyomi.data.api.ApiMangaResponse
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.mangadex.org"

private val gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .serializeNulls()
    .create()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

interface MangadexApi {
    @GET("/manga")
    suspend fun getMangas(
        @Query("ids[]") ids: List<String>? = null,
        @Query("limit") limit: Int? = null,
        @Query("includes[]") includes: List<String>? = listOf("cover_art", "author")
    ): ApiFeedResponse

    @GET("/manga/{manga_id}?includes[]=cover_art&includes[]=author")
    suspend fun getManga(@Path("manga_id") id: String): ApiMangaResponse

    @GET("/manga/{manga_id}/feed?translatedLanguage[]=en&limit=500&order%5Bvolume%5D=asc&order%5Bchapter%5D=asc&includes[]=scanlation_group")
    suspend fun getChapters(@Path("manga_id") id: String): ApiChaptersResponse

//    @GET("/at-home/server/{chapter_id}")
//    suspend fun getChapterAndServer(@Path("chapter_id") chapterID: String): ApiAtHomeResponse
}

object Mangadex{
    val retrofitService : MangadexApi by lazy{
        retrofit.create(MangadexApi::class.java)
    }
}