package com.bangkit.ayamhub.ui.farmform

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FarmFormViewModel(
    private val farmRepository: FarmRepository,
    private val userRepository: UserRepository
    ) : ViewModel() {

    val provinceId = farmRepository.provinsiId

    val kabupatenId = farmRepository.kabupatenId

    val getProvinsi = farmRepository.getProvinsi()

    val getKabupaten = farmRepository.getKabupaten()

    val getKecamatan = farmRepository.getKecamatan()

    private val _detectionImage = MutableLiveData<Bitmap>()
    val detectionImage: LiveData<Bitmap> = _detectionImage

    fun saveImage(bitmap: Bitmap) {
        _detectionImage.value = bitmap
    }

    fun setLevel(level: String) {
        viewModelScope.launch {
            userRepository.setLevel(level)
        }
    }

    fun uploadFarm(
        image: MultipartBody.Part,
        name: RequestBody,
        type: RequestBody,
        price: RequestBody,
        age: RequestBody,
        weight: RequestBody,
        stock: RequestBody,
        note: RequestBody,
        address: RequestBody,
        status: RequestBody
    ) = farmRepository.createFarm(image, name, type, price, age, weight, stock, note, address, status)

    fun getMyFarm() = farmRepository.getMyFarm()

    fun updateMyFarm(
        image: MultipartBody.Part,
        name: RequestBody,
        type: RequestBody,
        price: RequestBody,
        age: RequestBody,
        weight: RequestBody,
        stock: RequestBody,
        note: RequestBody,
        address: RequestBody,
        status: RequestBody
    ) = farmRepository.updateMyFarm(image, name, type, price, age, weight, stock, note, address, status)
}