package com.bangkit.ayamhub.ui.login.peternak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.ayamhub.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginPeternakanViewModel(private val repository: UserRepository) : ViewModel() {

    fun signIn(email: String, password: String) = repository.signInPeternak(email, password)

    fun saveToken(token: String) {
        viewModelScope.launch {
            repository.saveToken(token)
        }
    }

}