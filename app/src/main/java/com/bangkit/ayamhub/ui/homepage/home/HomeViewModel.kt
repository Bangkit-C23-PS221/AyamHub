package com.bangkit.ayamhub.ui.homepage.home

import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository

class HomeViewModel(
    private val farmRepo: FarmRepository
) : ViewModel() {

    val provinceId = farmRepo.provinsiId

    val kabupatenId = farmRepo.kabupatenId

    val getProvinsi = farmRepo.getProvinsi()

    val getKabupaten = farmRepo.getKabupaten()

    val getKecamatan = farmRepo.getKecamatan()

    fun getListFarm() = farmRepo.getAllFarm()

}