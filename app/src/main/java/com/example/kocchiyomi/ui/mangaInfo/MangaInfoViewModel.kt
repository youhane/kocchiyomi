package com.example.kocchiyomi.ui.mangaInfo

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.Mangadex
import com.example.kocchiyomi.data.api.ApiChaptersResponse
import com.example.kocchiyomi.data.model.Chapter
import com.example.kocchiyomi.data.model.Manga
import com.example.kocchiyomi.utils.AuthUtil
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.coroutines.launch
import java.util.*

class MangaInfoViewModel() : ViewModel() {
    private val _chapters = MutableLiveData<List<Chapter>>()
    val chapters: LiveData<List<Chapter>> = _chapters
    private lateinit var chaptersResponse: ApiChaptersResponse
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val mangaIdResponse = MutableLiveData<Boolean>()
    val firebaseMangaIdResponse: LiveData<Boolean> = mangaIdResponse

//    private val databaseChaptersResponse = MutableLiveData<List<Chapter>>()
//    private val firestoreChaptersResponse: LiveData<List<Chapter>> = databaseChaptersResponse

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    private fun insertChapter(id: String, chapterList: List<Chapter>) {
        viewModelScope.launch {
            try {
                for (chap in chapterList) {
                    chap.id?.let {
                        firestore.collection("mangas")
                            .document(id)
                            .collection("chapters")
                            .document(it)
                            .set(chap)
                    }
                }
            } catch (e: Exception) {
                Log.w("insert Chap Excp", e.toString())
            }
        }
    }

    fun getChapters(id: String) {
        viewModelScope.launch {
            try {
                var chapterList: List<Chapter> = emptyList()
                firestore.collection("mangas")
                    .document(id)
                    .collection("chapters")
                    .orderBy("attributes.publishAt", Query.Direction.ASCENDING)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            Log.w("Firestore error", "Listen failed.", e)
                            return@addSnapshotListener
                        }

                        if (snapshot != null) {
                            for (doc: DocumentChange in snapshot?.documentChanges!!) {
                                if (doc.type == DocumentChange.Type.ADDED) {
                                    chapterList =
                                        chapterList + doc.document.toObject(Chapter::class.java)
                                }
                            }
                            if (chapterList.isNotEmpty()) {

                                _chapters.value = chapterList
                            }
                            else {
                                getChapterFromMangaDex(id)
                            }
                        }
                    }

            } catch (e: Exception) {
                chaptersResponse = ApiChaptersResponse("", emptyList(), 500, 0)
                Log.w("Firestore Exception", e.toString())
            }
        }
    }

    private fun getChapterFromMangaDex(id: String) {
        viewModelScope.launch {
            try {
                chaptersResponse = Mangadex.retrofitService.getChapters(id)
                _chapters.value = chaptersResponse.data
                insertChapter(id, chaptersResponse.data)
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
                        .document(AuthUtil.getAuthId())
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
                    .document(AuthUtil.getAuthId())
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

    fun mangaIdInLibrary(id: String) {
        viewModelScope.launch {
            try {
                val library = firestore.collection("users")
                    .document(AuthUtil.getAuthId())
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
                Log.w("mangaIdInLib Exception", e.toString())
            }
        }
    }
//    fun saveToLibrary(id: String) = mangaDao.save(MangaEntity(id))
//    fun delete(id: String) = mangaDao.delete(id)
}

class MangaInfoViewModelFactory(
//    private val chapterDao: ChapterDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MangaInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MangaInfoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}