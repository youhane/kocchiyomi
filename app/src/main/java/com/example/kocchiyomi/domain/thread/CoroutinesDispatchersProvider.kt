package com.example.kocchiyomi.domain.thread

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.rx3.asCoroutineDispatcher

class CoroutinesDispatchersProvider(
    rxSchedulerProvider: IRxScheduler
    ) : ICoroutinesDispatchers {
        override val main: CoroutineDispatcher = rxSchedulerProvider.main.asCoroutineDispatcher()
        override val io: CoroutineDispatcher = rxSchedulerProvider.io.asCoroutineDispatcher()
}