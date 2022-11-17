package com.example.kocchiyomi.model.SearchByTitle


import com.google.gson.annotations.SerializedName

data class Title(
    @SerializedName("en")
    val en: String
)