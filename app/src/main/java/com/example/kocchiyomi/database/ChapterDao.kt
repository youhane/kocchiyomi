package com.example.kocchiyomi.database

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kocchiyomi.data.entity.ChapterEntity
import com.example.kocchiyomi.data.model.Chapter
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(chapter: ChapterEntity)
//
//    @Update
//    suspend fun update(chapter: ChapterEntity)
//
//    @Delete
//    suspend fun delete(chapter: ChapterEntity)
//
//    @Query("SELECT * from chapter")
//    fun getChapters(): LiveData<List<ChapterEntity>>
}

