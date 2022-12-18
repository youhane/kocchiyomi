package com.example.kocchiyomi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kocchiyomi.data.entity.ChapterEntity

@Database(entities = [ChapterEntity::class], version = 1, exportSchema = false)
abstract class ChapterRoomDatabase: RoomDatabase() {
    abstract fun chapterDao(): ChapterDao

    companion object {
        @Volatile
        private var INSTANCE: ChapterRoomDatabase? = null

        fun getDatabase(context: Context): ChapterRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChapterRoomDatabase::class.java,
                    "chapter_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}