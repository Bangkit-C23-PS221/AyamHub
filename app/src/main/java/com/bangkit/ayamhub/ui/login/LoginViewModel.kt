package com.bangkit.ayamhub.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.ayamhub.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun signIn(email: String, password: String) = repository.signIn(email, password)

    fun setToken(token: String) {
        viewModelScope.launch {
            repository.setToken(token)
        }
    }
    fun setId(id: String) {
        viewModelScope.launch {
            repository.setId(id)
        }
    }
    fun setName(name: String) {
        viewModelScope.launch {
            repository.setName(name)
        }
    }
    fun setEmail(email: String) {
        viewModelScope.launch {
            repository.setEmail(email)
        }
    }
    fun setPhone(phone: String) {
        viewModelScope.launch {
            repository.setPhone(phone)
        }
    }
    fun setLevel(level: String) {
        viewModelScope.launch {
            repository.setLevel(level)
        }
    }
}