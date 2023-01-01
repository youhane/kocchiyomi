package com.example.kocchiyomi.ui.auth.register

import android.net.Uri

interface IRegister {

    data class User(
        val email: String,
        val password: String,
        val confirmPassword: String,
        val username: String,
    )
}