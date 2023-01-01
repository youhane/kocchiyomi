package com.example.kocchiyomi.domain.thread

import kotlinx.coroutines.CoroutineDispatcher

interface ICoroutinesDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}