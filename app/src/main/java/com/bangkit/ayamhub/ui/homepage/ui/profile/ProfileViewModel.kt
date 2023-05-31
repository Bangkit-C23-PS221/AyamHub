package com.bangkit.ayamhub.ui.homepage.ui.profile

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.UserRepository

class ProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val getName = repository.getName()
    val getEmail = repository.getEmail()
    val getPhone = repository.getPhone()

}