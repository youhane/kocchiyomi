package com.example.kocchiyomi.database

import android.content.Context
import androidx.room.*
import com.example.kocchiyomi.data.entity.MangaEntity

@Database(entities = [MangaEntity::class], version = 1)
abstract class MangaRoomDatabase: RoomDatabase() {
    abstract fun mangaDao(): MangaDao

    companion object{
        @Volatile
        private var INSTANCE: MangaRoomDatabase? = null

        fun getDatabase(context: Context): MangaRoomDatabase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    MangaRoomDatabase::class.java,
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