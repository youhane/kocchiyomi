package com.example.kocchiyomi.ui.auth.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kocchiyomi.ui.auth.register.RegisterViewModel
import com.example.kocchiyomi.ui.mangaInfo.MangaInfoViewModel
import com.example.kocchiyomi.utils.ErrorMessage
import com.example.kocchiyomi.utils.LoadState
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {
    val navigateToHomeMutableLiveData = MutableLiveData<Boolean?>()
    val switchToRegisterMutableLiveData = MutableLiveData<Boolean?>()
    val loadingState = MutableLiveData<LoadState>()

    private val emailMatch = MutableLiveData<Boolean>()
    private val passwordMatch = MutableLiveData<Boolean>()

    fun login(auth: FirebaseAuth, email: String, password: String): LiveData<LoadState> {
        loadingState.value = LoadState.LOADING

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            loadingState.value = LoadState.SUCCESS
            navigateToHomeMutableLiveData.value = true
            switchToRegisterMutableLiveData.value = false
        }.addOnFailureListener {
            ErrorMessage.errorMessage = it.message
            loadingState.value = LoadState.FAILURE
        }
        return loadingState
    }

    fun doneNavigating() {
        navigateToHomeMutableLiveData.value = null
    }

    fun isEmailMatch(it: String): LiveData<Boolean> {
        emailMatch.value =  Patterns.EMAIL_ADDRESS.matcher(it).matches()
        return emailMatch
    }

    fun isPasswordMatch(it: String): LiveData<Boolean>  {
        passwordMatch.value = it.length >= 6
        return passwordMatch
    }

    fun enableSwitch() {
        switchToRegisterMutableLiveData.value = true
    }

    fun doneSwitch() {
        switchToRegisterMutableLiveData.value = null
    }

    class LoginViewModelFactory(
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}