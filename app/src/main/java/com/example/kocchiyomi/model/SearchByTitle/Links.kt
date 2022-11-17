package com.example.kocchiyomi.model.SearchByTitle


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("al")
    val al: String,
    @SerializedName("ap")
    val ap: String,
    @SerializedName("engtl")
    val engtl: String,
    @SerializedName("mal")
    val mal: String,
    @SerializedName("mu")
    val mu: String,
    @SerializedName("raw")
    val raw: String
)