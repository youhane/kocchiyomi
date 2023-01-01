package com.example.kocchiyomi.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

@Suppress("unused")
inline val Any?.unit
    get() = Unit

inline fun Context.toast(
    @StringRes message: Int,
    short: Boolean = true,
) = this.toast(getString(message), short)

inline fun Context.toast(
    message: String,
    short: Boolean = true,
) =
    Toast.makeText(
        this,
        message,
        if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).apply { show() }!!