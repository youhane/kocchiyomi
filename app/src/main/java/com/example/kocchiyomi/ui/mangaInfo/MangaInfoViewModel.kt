package com.example.kocchiyomi.ui.mangaInfo

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.Mangadex
import com.example.kocchiyomi.data.api.ApiChaptersResponse
import com.example.kocchiyomi.data.model.Chapter
import com.example.kocchiyomi.data.model.Manga
import com.example.kocchiyomi.data.model.MangaEntity
import com.example.kocchiyomi.data.model.User
import com.example.kocchiyomi.database.MangaDao
import com.example.kocchiyomi.utils.AuthUtil
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

class MangaInfoViewModel(private val mangaDao: MangaDao) : ViewModel() {
    private val _chapters = MutableLiveData<List<Chapter>>()
    private lateinit var chaptersResponse: ApiChaptersResponse
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val mangaIdResponse = MutableLiveData<Boolean>()
    val firebaseMangaIdResponse: LiveData<Boolean> = mangaIdResponse

    val chapters: LiveData<List<Chapter>> = _chapters

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }


    fun getChapters(id: String) {
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

    fun saveToLibrary(manga: Manga) {
            manga.timestamp = Timestamp(Date())
            viewModelScope.launch {
            try {
                manga.id?.let {
                    firestore.collection("users")
                        .document(AuthUtil.authId)
                        .collection("library")
                        .document(it)
                        .set(manga)
                        .addOnSuccessListener {
                            Log.d("Firebase", "Library Saved")
                        }.addOnFailureListener {
                            Log.w("Firebase Save Exception", it.message.toString())
                        }
                }
            } catch (e: Exception) {
                Log.w("Add To Lib Excp", e.toString())
            }
        }
    }

    fun removeFromLibrary(id: String) {
        viewModelScope.launch {
            try {
                firestore.collection("users")
                    .document(AuthUtil.authId)
                    .collection("library")
                    .document(id)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("Firebase", "Library Deleted")
                    }.addOnFailureListener {
                        Log.w("Firebase Del Exception", it.message.toString())
                    }
            } catch (e: Exception) {
                Log.w("Remove From Lib Excp", e.toString())
            }

        }
    }

    fun getById(id: String) {
        viewModelScope.launch {
            try {
                val library = firestore.collection("users")
                    .document(AuthUtil.authId)
                    .collection("library")
                    .document(id)

                mangaIdResponse.value = false
                library.get().addOnSuccessListener { document ->
                    if (document != null) {
                        mangaIdResponse.value = document.exists()
                    }
                }
                    .addOnFailureListener {
                        Log.w("Firebase Get Failed", it.message.toString())
                    }

            } catch (e: Exception) {
                Log.w("getById Exception", e.toString())
            }
        }
    }
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