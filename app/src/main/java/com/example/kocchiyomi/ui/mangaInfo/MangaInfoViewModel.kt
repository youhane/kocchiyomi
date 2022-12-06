package com.example.kocchiyomi.ui.mangaInfo

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.Mangadex
import com.example.kocchiyomi.data.api.ApiChaptersResponse
import com.example.kocchiyomi.data.model.Chapter
import com.example.kocchiyomi.data.model.MangaEntity
import com.example.kocchiyomi.database.MangaDao
import kotlinx.coroutines.launch

class MangaInfoViewModel(private val mangaDao: MangaDao) : ViewModel() {
    private val _chapters = MutableLiveData<List<Chapter>>()
    private lateinit var chaptersResponse: ApiChaptersResponse

    val chapters: LiveData<List<Chapter>> = _chapters

    fun getChapters(id: String){
        viewModelScope.launch {
            try {
                chaptersResponse = Mangadex.retrofitService.getChapters(id)
                _chapters.value = chaptersResponse.data
            } catch (e: Exception) {
                chaptersResponse = ApiChaptersResponse("", emptyList(), 500, 0)
                Log.w("Mangadex API Exception", e.toString())
            }
        }
    }
//    fun getById(id: String) = mangaDao.getById(id)
//    fun saveToLibrary(id: String) = mangaDao.save(MangaEntity(id))
//    fun delete(id: String) = mangaDao.delete(id)
}

class MangaInfoViewModelFactory(
    private val mangaDao: MangaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MangaInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MangaInfoViewModel(mangaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}