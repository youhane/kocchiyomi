package com.example.kocchiyomi.ui.library

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.api.ApiChaptersResponse
import com.example.kocchiyomi.data.api.ApiLibraryResponse
import com.example.kocchiyomi.data.model.Manga
import com.example.kocchiyomi.database.MangaDao
import com.example.kocchiyomi.utils.AuthUtil
import com.example.kocchiyomi.utils.FirestoreHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class LibraryViewModel(private val mangaDao: MangaDao) : ViewModel() {

    private var firestore: FirebaseFirestore = FirestoreHelper.firestoreInstance

    private val response = MutableLiveData<List<Manga>>()

    val libraryResponse: LiveData<List<Manga>> = response

    fun getLibrary() {
        viewModelScope.launch {
            try {
//                Log.d("user refresh id", AuthUtil.getAuthId())
//                FirebaseAuth.getInstance().currentUser?.let { Log.d("user refresh id 2", it.uid) }
                firestore.collection("users")
                    .document(AuthUtil.getAuthId())
                    .collection("library").addSnapshotListener(
                        object: EventListener<QuerySnapshot>{
                            override fun onEvent(
                                value: QuerySnapshot?,
                                error: FirebaseFirestoreException?
                            ) {
                                if (error != null) {
                                    Log.e("Firestore error", error.message.toString())
                                    return
                                }
                                Log.d("Get Library", "Firestore")
                                var mangaList = ArrayList<Manga>()
                                for (doc: DocumentChange in value?.documentChanges!!){
                                    if (doc.type == DocumentChange.Type.ADDED) {
                                        mangaList.add(doc.document.toObject(Manga::class.java))
                                    }
                                }
                                response.value = mangaList.toList()
                            }
                        }
                    )
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