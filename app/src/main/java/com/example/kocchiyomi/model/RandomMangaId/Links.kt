package com.example.kocchiyomi.model.RandomMangaId


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("al")
    val al: String,
    @SerializedName("amz")
    val amz: String,
    @SerializedName("ap")
    val ap: String,
    @SerializedName("mal")
    val mal: String,
    @SerializedName("mu")
    val mu: String,
    @SerializedName("raw")
    val raw: String
)