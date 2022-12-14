package com.example.kocchiyomi.utils

enum class LoadState {
    SUCCESS, FAILURE, LOADING
}

class ErrorMessage {
    companion object {
        var errorMessage: String? = "Something went wrong"
    }
}

