package com.example.kocchiyomi

import android.app.Application
import com.example.kocchiyomi.database.MangaRoomDatabase
import com.google.firebase.database.FirebaseDatabase

class KocchiyomiApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
//        val scoresRef = Firebase.database.getReference("scores")
//        scoresRef.keepSynced(true)
    }

//    val database: MangaRoomDatabase by lazy {
//        MangaRoomDatabase.getDatabase(this)
//    }

//    val chapterDatabase: ChapterRoomDatabase by lazy {
//        ChapterRoomDatabase.getDatabase(this)
//    }
}