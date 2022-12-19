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
import kotlin.collections.ArrayList

class MangaInfoViewModel() : ViewModel() {
    private val _chapters = MutableLiveData<List<Chapter>>()
    val chapters: LiveData<List<Chapter>> = _chapters
    private lateinit var chaptersResponse: ApiChaptersResponse
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val mangaIdResponse = MutableLiveData<Boolean>()
    val firebaseMangaIdResponse: LiveData<Boolean> = mangaIdResponse

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
        Log.d("Get Chapter", "Call")
        viewModelScope.launch {
            try {
                val collection = firestore.collection("mangas")
                    .document(id)
                    .collection("chapters")

                val countQuery = collection.count()

                countQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val snapshot = task.result
                        val listCount = snapshot.count.toInt()
                        Log.d("Firestore Success", "Count: $listCount")
                        if (listCount == 0) {
                            Log.d("Get Chapter", "Mangadex")
                            getChapterFromMangaDex(id)
                        }
                        else {
                            Log.d("Get Chapter", "Firestore")
                            getChapterFromFirestore(id)
                        }
                    } else {
                        Log.d("Firestore Failed", "Count failed: $task.exception")
                        getChapterFromFirestore(id)
                    }
                }
            } catch (e: Exception) {
                chaptersResponse = ApiChaptersResponse("", emptyList(), 500, 0)
                Log.w("Firestore Exception", e.toString())
            }
        }
    }

    private fun getChapterFromFirestore(id: String) {
        viewModelScope.launch {
            try {
                firestore.collection("mangas")
                    .document(id)
                    .collection("chapters")
                    .orderBy("attributes.publishAt", Query.Direction.ASCENDING)
                    .addSnapshotListener(
                        object : EventListener<QuerySnapshot>{
                            override fun onEvent(
                                value: QuerySnapshot?,
                                error: FirebaseFirestoreException?
                            ) {
                                if (error != null) {
                                    Log.w("Firestore error", "Listen failed.", error)
                                    return
                                }
                                var chapterTempList = ArrayList<Chapter>()
                                for (doc: DocumentChange in value?.documentChanges!!) {
                                    if (doc.type == DocumentChange.Type.ADDED) {
                                        chapterTempList.add(doc.document.toObject(Chapter::class.java))
                                    }
                                }
                                _chapters.value = chapterTempList.toList()
                                Log.d("Get ChapterFirestore", "Success")
                            }
                        }
                    )
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
                Log.d("Get ChapterMangadex", "Success")
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