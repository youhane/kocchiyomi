package com.example.kocchiyomi

import android.util.Log
import androidx.lifecycle.*
import com.example.kocchiyomi.data.Mangadex
import com.example.kocchiyomi.data.api.ApiFeedResponse
import com.example.kocchiyomi.data.model.User
import com.example.kocchiyomi.utils.AuthUtil
import com.example.kocchiyomi.utils.FirestoreHelper
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val response = MutableLiveData<User>()
    val userDataResponse: LiveData<User> = response

    private val isSignOutResponse = MutableLiveData<Boolean>()
    val isSignOut: LiveData<Boolean> = isSignOutResponse

    fun getUserData(){
        viewModelScope.launch {
            try {
                response.value = AuthUtil.getUserDetail()
            } catch (e: Exception){
                Log.e("User Data Exception", e.toString())
            }

        }
    }

    fun signOut(){
        viewModelScope.launch {
            try {
                AuthUtil.firebaseAuthInstance().signOut()
                isSignOutResponse.value = true
                Log.d("User", AuthUtil.firebaseAuthInstance().currentUser?.uid.toString())
            } catch (e: Exception){
                Log.e("Logout Exception", e.toString())
            }

        }
    }
}

class MainViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}