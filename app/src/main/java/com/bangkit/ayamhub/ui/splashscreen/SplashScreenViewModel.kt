package com.bangkit.ayamhub.ui.splashscreen

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.UserRepository

class SplashScreenViewModel(
    repo: UserRepository
) : ViewModel() {

    val getUserData = repo.getToken()
}