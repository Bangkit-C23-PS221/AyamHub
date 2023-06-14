package com.bangkit.ayamhub.ui.homepage.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository

class HomeViewModel(
    private val farmRepo: FarmRepository
) : ViewModel() {

    val provinceId = farmRepo.provinsiId

    val kabupatenId = farmRepo.kabupatenId

    private val _isItemEmpty = MutableLiveData<Boolean>()
    val isItemEmpty: LiveData<Boolean> = _isItemEmpty

    fun checkItem(isEmpty: Boolean) {
        _isItemEmpty.value = isEmpty
    }

    fun getProvinsi() = farmRepo.getProvinsi()

    fun getKabupaten() = farmRepo.getKabupaten()

    fun getKecamatan() = farmRepo.getKecamatan()

    fun getListFarm() = farmRepo.getAllFarm()

}