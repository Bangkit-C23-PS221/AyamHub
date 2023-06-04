package com.bangkit.ayamhub.ui.farmform

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.FarmRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FarmFormViewModel(private val repository: FarmRepository) : ViewModel() {

    val provinceId = repository.provinsiId

    val kabupatenId = repository.kabupatenId

    val getProvinsi = repository.getProvinsi()

    val getKabupaten = repository.getKabupaten()

    val getKecamatan = repository.getKecamatan()

    private val _detectionImage = MutableLiveData<Bitmap>()
    val detectionImage: LiveData<Bitmap> = _detectionImage
    fun saveImage(bitmap: Bitmap) {
        _detectionImage.value = bitmap
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
    ) = repository.createFarm(image, name, type, price, age, weight, stock, note, address, status)

}