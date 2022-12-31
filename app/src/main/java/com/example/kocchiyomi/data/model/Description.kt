package com.example.kocchiyomi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Description(
    val en: String? = null,
): Parcelable