package com.example.kocchiyomi.domain.thread

import io.reactivex.rxjava3.core.Scheduler

interface IRxScheduler {
    val main: Scheduler
    val io: Scheduler
}