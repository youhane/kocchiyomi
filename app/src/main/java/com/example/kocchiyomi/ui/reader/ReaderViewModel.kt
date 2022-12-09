package com.example.kocchiyomi.ui.reader

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.Mangadex
import com.example.kocchiyomi.data.api.ApiReaderResponse
import com.example.kocchiyomi.database.MangaDao
import kotlinx.coroutines.launch

class ReaderViewModel() : ViewModel() {
    private val _pages = MutableLiveData<ApiReaderResponse>()
    val response: LiveData<ApiReaderResponse> = _pages

    fun getChapterData(chapterID: String){
        viewModelScope.launch{
            try{
                _pages.value = Mangadex.retrofitService.getChapterAndServer(chapterID)
            } catch (e: Exception){
                Log.w("Mangadex API Exception", e.toString())
            }
        }
    }
}

