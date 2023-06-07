package com.bangkit.ayamhub.ui.homepage.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.ayamhub.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val getName = repository.getName()
    val getEmail = repository.getEmail()
    val getPhone = repository.getPhone()
    val getLevel = repository.getLevel()

    fun logOut() {
        viewModelScope.launch {
            repository.setToken("")
            repository.setLevel("")
            repository.setPhone("")
            repository.setName("")
            repository.setId("")
        }
    }
}