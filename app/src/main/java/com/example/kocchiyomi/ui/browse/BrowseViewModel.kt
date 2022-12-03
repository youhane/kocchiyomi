package com.example.kocchiyomi.ui.browse

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.Mangadex
import com.example.kocchiyomi.data.api.ApiFeedResponse
import kotlinx.coroutines.launch

class BrowseViewModel : ViewModel() {

    private val response = MutableLiveData<ApiFeedResponse>()

    val feedResponse: LiveData<ApiFeedResponse> = response

    fun getFeed(){
        viewModelScope.launch {
            try {
                response.value = Mangadex.retrofitService.getMangas(limit = 30)
            } catch (e: Exception){
                Log.w("Manga Browse Exception", e.toString())
            }

        }
    }
}

class BrowseViewModelFactory():ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowseViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return BrowseViewModel(mangaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}