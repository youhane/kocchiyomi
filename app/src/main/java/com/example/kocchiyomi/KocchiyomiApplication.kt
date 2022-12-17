package com.example.kocchiyomi

import android.app.Application
import com.example.kocchiyomi.database.DatabaseApp
import com.google.firebase.database.FirebaseDatabase

class KocchiyomiApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    val database: DatabaseApp by lazy {
        DatabaseApp.getDatabase(this)
    }
}