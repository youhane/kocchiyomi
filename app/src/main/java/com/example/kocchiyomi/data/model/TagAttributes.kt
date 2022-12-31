package com.example.kocchiyomi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagAttributes(
    val group: String? = null,
    val name: Name? = null,
    val version: Int? = null
): Parcelable