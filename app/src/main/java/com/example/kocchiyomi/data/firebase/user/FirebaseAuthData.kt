package com.example.kocchiyomi.data.firebase.user

import androidx.lifecycle.LiveData
import arrow.core.Either
import arrow.core.toOption
import arrow.core.Option
import arrow.core.right
import com.example.kocchiyomi.data.firebase.model._User
import com.example.kocchiyomi.domain.thread.CoroutinesDispatchersProvider
import com.example.kocchiyomi.domain.thread.RxSchedulerProvider
import com.example.kocchiyomi.utils.unit
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.snapshots
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onErrorReturn
import kotlinx.coroutines.flow.subscribeOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthData(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
    private val firebaseFirestore: FirebaseFirestore,
    private val rxSchedulerProvider: RxSchedulerProvider,
    private val dispatchersProvider: CoroutinesDispatchersProvider,
    ): IFirebaseAuthData {

    override suspend fun register(
        email: String,
        username: String,
        password: String,
    ) = Either.catch {
        withContext(dispatchersProvider.io) {
            val authRes = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authRes.user ?: throw IllegalStateException("User null")

            coroutineScope {
                firebaseFirestore
                    .document("users/${user.uid}")
                    .set(
                        _User(
                            uid = user.uid,
                            userName = username,
                            email = email
                        )
                    )
                    .await()

                awaitAll(
                    async {
                        user
                            .updateProfile(
                                UserProfileChangeRequest
                                    .Builder()
                                    .setDisplayName(username)
                                    .build()
                            ).await()
                    },
                )
            }
            Unit
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ) = Either.catch {
        withContext(dispatchersProvider.io) {
            firebaseAuth.signInWithEmailAndPassword(email, password).await().unit
        }
    }

    override suspend fun logOut() = Either.catch {
        withContext(dispatchersProvider.io) {
            firebaseAuth.signOut()
        }
    }


//    private val userObservable: Observable<Either<Throwable, _User?>> by lazy {
//        Observable
//            .create<Option<String>> { emitter ->
//                val authStateListener = FirebaseAuth.AuthStateListener { auth ->
//                    if (!emitter.isDisposed) {
//                        val uid = auth.currentUser.toOption().map { it.uid }
//                        emitter.onNext(uid)
//                    }
//                }
//
//                firebaseAuth.addAuthStateListener(authStateListener)
//                emitter.setCancellable {
//                    firebaseAuth.removeAuthStateListener(authStateListener)
//                }
//            }
//            .distinctUntilChanged()
//            .switchMap { uidOptional ->
//                uidOptional.fold(
//                    ifEmpty = { Observable.just(null.right()) },
//                    ifSome = { uid ->
//                        firebaseFirestore
//                            .document("users/$uid")
//                            .snapshots()
//                            .map<Either<Throwable, _User?>> { it.toObject(_User::class.java).right() }
//                            .onErrorReturn { it.left() }
//                            .subscribeOn(rxSchedulerProvider.io)
//                    }
//                )
//            }
//            .subscribeOn(rxSchedulerProvider.io)
//            .replay(1)
//            .refCount()
//    }
//    override fun userObservable() = userObservable
}


