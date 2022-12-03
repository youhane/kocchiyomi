package com.example.kocchiyomi.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.kocchiyomi.data.model.MangaEntity

@Database(entities = [MangaEntity::class], version = 1)
abstract class DatabaseApp: RoomDatabase() {
    abstract fun mangaDao(): MangaDao

    companion object{
        @Volatile
        private var INSTANCE: DatabaseApp? = null

        fun getDatabase(context: Context): DatabaseApp {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    DatabaseApp::class.java,
                    "database_app"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }

//    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
//        TODO("Not yet implemented")
//    }
//
//    override fun createInvalidationTracker(): InvalidationTracker {
//        TODO("Not yet implemented")
//    }
//
//    override fun clearAllTables() {
//        TODO("Not yet implemented")
//    }
}