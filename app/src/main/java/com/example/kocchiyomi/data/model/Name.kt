package com.example.kocchiyomi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Name(
    val en: String? = null
): Parcelable