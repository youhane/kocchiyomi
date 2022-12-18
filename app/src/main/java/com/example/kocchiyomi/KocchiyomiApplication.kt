package com.example.kocchiyomi

import android.app.Application
import com.example.kocchiyomi.database.DatabaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class KocchiyomiApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
//        val scoresRef = Firebase.database.getReference("scores")
//        scoresRef.keepSynced(true)
    }

    val database: DatabaseApp by lazy {
        DatabaseApp.getDatabase(this)
    }
}