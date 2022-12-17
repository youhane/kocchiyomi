package com.example.kocchiyomi.ui.library

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.api.ApiLibraryResponse
import com.example.kocchiyomi.database.MangaDao
import kotlinx.coroutines.launch

class LibraryViewModel(private val mangaDao: MangaDao) : ViewModel() {

    private val response = MutableLiveData<ApiLibraryResponse>()

    val libraryResponse: LiveData<ApiLibraryResponse> = response

    fun getLibrary() {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {
                Log.w("Library Exception", e.toString())
            }
        }
    }
}

class LibraryViewModelFactory(private val mangaDao: MangaDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryViewModel(mangaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}