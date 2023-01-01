package com.example.kocchiyomi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Title(
    val en: String?="No Title"
): Parcelable