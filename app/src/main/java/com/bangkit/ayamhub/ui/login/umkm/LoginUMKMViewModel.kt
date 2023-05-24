package com.bangkit.ayamhub.ui.login.umkm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.ayamhub.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginUMKMViewModel(private val repository: UserRepository) : ViewModel() {

    fun signIn(email: String, password: String) = repository.signInUmkm(email, password)

    fun saveToken(token: String) {
        viewModelScope.launch {
            repository.saveToken(token)
        }
    }

}