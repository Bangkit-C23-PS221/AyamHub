package com.bangkit.ayamhub.ui.farmer

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.FarmRepository

class FarmerVIewModel(private val repository: FarmRepository) : ViewModel() {

    fun getMyFarm() = repository.getMyFarm()

}