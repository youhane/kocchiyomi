package com.example.kocchiyomi.model.SearchByTitle


import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("en")
    val en: String,
    @SerializedName("pt-br")
    val ptBr: String
)