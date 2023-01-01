package com.example.kocchiyomi

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class KocchiyomiApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
//        val scoresRef = Firebase.database.getReference("scores")
//        scoresRef.keepSynced(true)
    }
}