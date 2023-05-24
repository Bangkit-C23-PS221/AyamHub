package com.bangkit.ayamhub.ui.register.peternak

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.UserRepository

class RegisterPeternakViewModel(private val repository: UserRepository) : ViewModel() {

    fun signUp(
        name: String,
        username: String,
        password: String,
        email: String,
        phoneNumber: String
    ) = repository.signUpPeternak(name, username, password, email, phoneNumber)

}