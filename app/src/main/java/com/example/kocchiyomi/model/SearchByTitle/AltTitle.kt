package com.example.kocchiyomi.model.SearchByTitle


import com.google.gson.annotations.SerializedName

data class AltTitle(
    @SerializedName("ar")
    val ar: String,
    @SerializedName("bn")
    val bn: String,
    @SerializedName("en")
    val en: String,
    @SerializedName("he")
    val he: String,
    @SerializedName("hi")
    val hi: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ja")
    val ja: String,
    @SerializedName("ja-ro")
    val jaRo: String,
    @SerializedName("ko")
    val ko: String,
    @SerializedName("my")
    val my: String,
    @SerializedName("pt-br")
    val ptBr: String,
    @SerializedName("ru")
    val ru: String,
    @SerializedName("sr")
    val sr: String,
    @SerializedName("ta")
    val ta: String,
    @SerializedName("th")
    val th: String,
    @SerializedName("zh")
    val zh: String,
    @SerializedName("zh-hk")
    val zhHk: String
)