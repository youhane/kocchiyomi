package com.example.kocchiyomi.ui.browse

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.Mangadex
import com.example.kocchiyomi.data.api.ApiFeedResponse
import com.example.kocchiyomi.data.model.Chapter
import com.example.kocchiyomi.database.MangaDao
import com.google.firebase.firestore.*
import kotlinx.coroutines.launch

class BrowseViewModel() : ViewModel() {

    private val response = MutableLiveData<ApiFeedResponse>()
    val feedResponse: LiveData<ApiFeedResponse> = response

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    private fun getFeedFromMangadex() {
        viewModelScope.launch {
            try {
                response.value = Mangadex.retrofitService.getMangas(limit = 30)
                insertFeedToFirestore(response.value!!)
            } catch (e: Exception){
                Log.w("Manga Browse Exception", e.toString())
                getAlternativeFeedFromMangadex()
            }

        }
    }

    private fun getAlternativeFeedFromMangadex() {
        viewModelScope.launch {
            try {
                response.value = Mangadex.retrofitService.getAlternativeMangas(limit = 30)
                insertFeedToFirestore(response.value!!)
            } catch (e: Exception){
                Log.w("Manga Alt Browse Excpt", e.toString())
                getFeedFromFirestore()
            }

        }
    }

    private fun getFeedFromFirestore() {
        viewModelScope.launch {
            try {
                firestore.collection("browse")
                    .document("FeedResponse")
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            response.value = document.toObject(ApiFeedResponse::class.java)
                        } else {
                            response.value = ApiFeedResponse("", "", emptyList(), 30, 0, 0)
                            Log.d("Get Firestore", "No manga feedResponse document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        response.value = ApiFeedResponse("", "", emptyList(), 30, 0, 0)
                        Log.d("Get Firestore Failed", "Failed ", exception)
                    }
            } catch (e: Exception){
                response.value = ApiFeedResponse("", "", emptyList(), 30, 0, 0)
                Log.w("Manga Browse Exception", e.toString())
            }

        }
    }

    private fun insertFeedToFirestore(data: ApiFeedResponse) {
        viewModelScope.launch {
            try {
                firestore.collection("browse")
                    .document("FeedResponse")
                    .set(data)
            } catch (e: Exception) {
                Log.w("insert Manga Excp", e.toString())
            }
        }
    }

    fun getFeed() {
        getFeedFromMangadex()
    }
}

class BrowseViewModelFactory():ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BrowseViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}