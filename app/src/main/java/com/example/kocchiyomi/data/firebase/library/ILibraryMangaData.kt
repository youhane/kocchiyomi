package com.example.kocchiyomi.data.firebase.library

import arrow.core.Either
import com.example.kocchiyomi.data.firebase.model._LibraryManga
import io.reactivex.rxjava3.core.Observable

interface ILibraryMangaData {
    fun isInLibrary(id: String): Observable<Either<Throwable, Boolean>>

    fun LibraryMangas(): Observable<Either<Throwable, List<_LibraryManga>>>

    suspend fun removeFromLibrary(comic: _LibraryManga): Either<Throwable, Unit>

    suspend fun toggle(comic: _LibraryManga): Either<Throwable, Unit>

    fun update(comics: List<_LibraryManga>)
}