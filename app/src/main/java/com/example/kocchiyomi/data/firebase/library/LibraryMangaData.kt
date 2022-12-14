package com.example.kocchiyomi.data.firebase.library

import arrow.core.Either
import arrow.core.right
import com.example.kocchiyomi.data.firebase.model._LibraryManga
import com.example.kocchiyomi.domain.thread.CoroutinesDispatchersProvider
import com.example.kocchiyomi.domain.thread.RxSchedulerProvider
import com.example.kocchiyomi.utils.unit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onErrorReturn
import kotlinx.coroutines.flow.subscribeOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class LibraryMangaData (
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
    private val firebaseFirestore: FirebaseFirestore,
    private val rxSchedulerProvider: RxSchedulerProvider,
    private val dispatchersProvider: CoroutinesDispatchersProvider,
    appCoroutineScope: CoroutineScope
) : ILibraryMangaData {
//    private val actor = appCoroutineScope.actor<List<_LibraryManga>>(capacity = Channel.BUFFERED) {
//        for (mangas in this) {
//            _updateMangas(mangas)
//        }
//    }

    //    @OptIn(ExperimentalTime::class)
//    private suspend fun _updateMangas(mangas: List<_LibraryManga>) {
//        measureTime {
//            val collection = favoriteCollectionForCurrentUserOrNull ?: return@measureTime
//            val documents = collection.get().await().documents
//
//            firebaseFirestore
//                .runBatch { writeBatch ->
//                    documents.fold(writeBatch) { batch, doc ->
//                        when (val manga = mangas.find { it.id == doc["id"] }) {
//                            null -> batch
//                            else -> batch.update(doc.reference, manga.asMap())
//                        }
//                    }
//                }.await()
//        }
//    }
    override fun isInLibrary(id: String): Observable<Either<Throwable, Boolean>> {
        TODO("Not yet implemented")
    }

    override fun LibraryMangas(): Observable<Either<Throwable, List<_LibraryManga>>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromLibrary(comic: _LibraryManga): Either<Throwable, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun toggle(comic: _LibraryManga): Either<Throwable, Unit> {
        TODO("Not yet implemented")
    }

    override fun update(comics: List<_LibraryManga>) {
        TODO("Not yet implemented")
    }


}