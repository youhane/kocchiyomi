package com.example.kocchiyomi.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.Mangadex
import com.example.kocchiyomi.data.api.ApiFeedResponse
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val response = MutableLiveData<ApiFeedResponse>()
    val searchResponse: LiveData<ApiFeedResponse> = response

    fun getSearchFeedFromMangadex(searchQuery: String) {
        viewModelScope.launch {
            try {
                response.value = Mangadex.retrofitService.getSearchMangas(title = searchQuery, limit = 30)
            } catch (e: Exception){
                Log.w("Manga Browse Exception", e.toString())
            }
        }
    }

}

class SearchViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}