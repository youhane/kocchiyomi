package com.example.kocchiyomi.utils

import android.util.Log
import com.example.kocchiyomi.data.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await


object AuthUtil {

    fun firebaseAuthInstance(): FirebaseAuth {
//        println("firebaseAuthInstance.:")
        return FirebaseAuth.getInstance()
    }

    fun getAuthId(): String {
        return firebaseAuthInstance().currentUser!!.uid
    }

    fun getAuthEmail(): String {
        return firebaseAuthInstance().currentUser!!.email.toString()
    }

    suspend fun getUserDetail(): User {
        var userNameRequest = "default"
        var uidRequest = "default"
        var emailRequest = "default"

        uidRequest = getAuthId()
        emailRequest = getAuthEmail()

        val db = FirestoreHelper.firestoreInstance
        try {
            userNameRequest = db.collection("users").document(getAuthId()).get().await().data?.get("userName") as String
        } catch (e: Exception) {
            Log.w("getUserId Exception", e.toString())
        }


        return User(uidRequest, userNameRequest, emailRequest)
    }
}