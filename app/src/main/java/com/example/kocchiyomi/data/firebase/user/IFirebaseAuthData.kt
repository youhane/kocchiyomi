package com.example.kocchiyomi.data.firebase.user

import arrow.core.Either
import com.example.kocchiyomi.data.firebase.model._User
import io.reactivex.rxjava3.core.Observable

interface IFirebaseAuthData {
    suspend fun register(
        email: String,
        username: String,
        password: String,
    ): Either<Throwable, Unit>

    suspend fun login(
        email: String,
        password: String
    ): Either<Throwable, Unit>

    suspend fun logOut(
    ): Either<Throwable, Unit>

//    fun userObservable(
//    ): Observable<Either<Throwable, _User?>>
}