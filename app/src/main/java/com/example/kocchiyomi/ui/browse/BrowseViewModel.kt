package com.example.kocchiyomi.ui.browse

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.Mangadex
import com.example.kocchiyomi.data.api.ApiFeedResponse
import com.example.kocchiyomi.database.MangaDao
import kotlinx.coroutines.launch

class BrowseViewModel(private val mangaDao: MangaDao) : ViewModel() {

    private val response = MutableLiveData<ApiFeedResponse>()

    val feedResponse: LiveData<ApiFeedResponse> = response

    private fun getAlternativeFeed() {
        viewModelScope.launch {
            try {
                response.value = Mangadex.retrofitService.getAlternativeMangas(limit = 30)
            } catch (e: Exception){
                Log.w("Manga Alt Browse Excpt", e.toString())
            }

        }
    }

    fun getFeed() {
        viewModelScope.launch {
            try {
                response.value = Mangadex.retrofitService.getMangas(limit = 30)
            } catch (e: Exception){
                Log.w("Manga Browse Exception", e.toString())
                getAlternativeFeed()
            }

        }
    }

//    fun getLibrary() = mangaDao.getAll()
}

class BrowseViewModelFactory(private val mangaDao: MangaDao):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BrowseViewModel(mangaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}