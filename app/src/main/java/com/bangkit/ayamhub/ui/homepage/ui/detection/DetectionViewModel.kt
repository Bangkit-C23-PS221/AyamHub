package com.bangkit.ayamhub.ui.homepage.ui.detection

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository

class DetectionViewModel(
    private val farmRepo: FarmRepository,
    private val userRepo: UserRepository
    ) : ViewModel() {

    private val _detectionImage = MutableLiveData<Bitmap>()
    val detectionImage: LiveData<Bitmap> = _detectionImage

    fun saveImage(bitmap: Bitmap) {
        _detectionImage.value = bitmap
    }
}