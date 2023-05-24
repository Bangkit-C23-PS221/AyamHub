package com.bangkit.ayamhub.ui.register.umkm

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.UserRepository

class RegisterUMKMViewModel(private val repository: UserRepository) : ViewModel() {

    fun signUp(
        name: String,
        username: String,
        password: String,
        email: String,
        phoneNumber: String
    ) = repository.signUpUmkm(name, username, password, email, phoneNumber)

}