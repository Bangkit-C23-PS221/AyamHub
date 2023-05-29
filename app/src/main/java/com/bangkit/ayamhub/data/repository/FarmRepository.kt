package com.bangkit.ayamhub.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bangkit.ayamhub.data.network.retrofit.ApiConfig
import com.bangkit.ayamhub.data.network.Result

class FarmRepository(private val apiConfig: ApiConfig) {

    val provinsiId = MutableLiveData<Int>()
    val kabupatenId = MutableLiveData<Int>()

    fun getAllFarm(token: String) = liveData {
        emit(Result.Loading)
        try {
            emit("Something")
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getProvinsi() = liveData {
        try {
            val response = apiConfig.getLocationApiService().getProvince()
            emit(response)
        } catch (e: Exception) {
            Log.e("FarmRepository", "OnFailure: ${e.message.toString()}")
        }
    }

    fun getKabupaten() = provinsiId.switchMap { selectedProvince ->
        liveData {
            try {
                val response = ApiConfig.getLocationApiService().getKabupaten(selectedProvince)
                emit(response)
            } catch (e: Exception) {
                Log.e("FarmRepository", "OnFailure: ${e.message.toString()}")
            }
        }
    }

    fun getKecamatan() = kabupatenId.switchMap { selectedKab ->
        liveData {
            try {
                val response = ApiConfig.getLocationApiService().getKecamatan(selectedKab)
                emit(response)
            } catch (e: Exception) {
                Log.e("FarmRepository", "OnFailure: ${e.message.toString()}")
            }
        }
    }

    companion object {
        private var INSTANCE:  FarmRepository? = null
        fun getInstance(
            apiConfig: ApiConfig
        ) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: FarmRepository(apiConfig)
        }.also { INSTANCE = it }
    }

}