package com.bangkit.ayamhub.ui.homepage.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository

class HomeViewModel(
    private val farmRepo: FarmRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    fun getToken() = userRepo.getToken()

    fun getAllFarm(token: String) = farmRepo.getAllFarm(token)

}