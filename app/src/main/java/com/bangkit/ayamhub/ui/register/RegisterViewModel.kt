package com.bangkit.ayamhub.ui.register

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.UserRepository

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    fun signUp(
        name: String,
        username: String,
        password: String,
        email: String,
        phoneNumber: String
    ) = repository.signUpUmkm(name, username, password, email, phoneNumber)

}