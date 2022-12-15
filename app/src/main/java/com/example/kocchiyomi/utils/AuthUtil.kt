package com.example.kocchiyomi.utils

import android.util.Log
import com.example.kocchiyomi.data.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

object AuthUtil {

    val firebaseAuthInstance: FirebaseAuth by lazy {
//        println("firebaseAuthInstance.:")
        FirebaseAuth.getInstance()
    }


    fun getAuthId(): String {
        return firebaseAuthInstance.currentUser!!.uid
    }

    fun getAuthEmail(): String? {
        return firebaseAuthInstance.currentUser!!.email
    }

    fun firebaseSignOut() {
        firebaseAuthInstance.signOut()
    }

    suspend fun getUserDetail(): User {
        var userNameRequest = "default"
        var uidRequest = "default"
        var emailRequest = "default"

        uidRequest = getAuthId().toString()
        emailRequest = getAuthEmail().toString()

        val db = FirestoreHelper.firestoreInstance
        userNameRequest = db.collection("users").document(getAuthId()).get().await().data?.get("userName") as String

        return User(uidRequest, userNameRequest, emailRequest)
    }
}