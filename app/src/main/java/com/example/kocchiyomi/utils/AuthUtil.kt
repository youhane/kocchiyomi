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

    val authId: String  by lazy {
        firebaseAuthInstance.currentUser!!.uid
    }

    val authEmail: String by lazy {
        firebaseAuthInstance.currentUser!!.email.toString()
    }

    val firebaseSignOut by lazy {
        firebaseAuthInstance.signOut()
    }

    suspend fun getUserDetail(): User {
        var userNameRequest = "default"
        var uidRequest = "default"
        var emailRequest = "default"

        uidRequest = authId
        emailRequest = authEmail

        val db = FirestoreHelper.firestoreInstance
        userNameRequest = db.collection("users").document(authId).get().await().data?.get("userName") as String

        return User(uidRequest, userNameRequest, emailRequest)
    }
}