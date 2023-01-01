package com.example.kocchiyomi.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

object FirestoreHelper {
    val firestoreInstance: FirebaseFirestore by lazy {

        val firebaseFirestore = FirebaseFirestore.getInstance()

        val settings = FirebaseFirestoreSettings.Builder()
            .build()
        firebaseFirestore.firestoreSettings = settings

        firebaseFirestore
    }
}