package com.bangkit.ayamhub.ui.farmer

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.FarmRepository

class FarmerVIewModel(repository: FarmRepository) : ViewModel() {

    val getMyFarm = repository.getMyFarm()

}