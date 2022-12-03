package com.example.kocchiyomi

import android.app.Application
import com.example.kocchiyomi.database.DatabaseApp

class KocchiyomiApplication: Application(){
    val database: DatabaseApp by lazy {
        DatabaseApp.getDatabase(this)
    }
}