package com.example.kocchiyomi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    val attributes: TagAttributes? = null,
    val id: String? = null,
    val type: String? = null
): Parcelable