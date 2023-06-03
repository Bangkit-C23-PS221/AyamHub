package com.bangkit.ayamhub.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bangkit.ayamhub.data.local.datastore.UserPreference
import com.bangkit.ayamhub.data.network.retrofit.ApiConfig
import com.bangkit.ayamhub.data.network.Result

class FarmRepository(
    private val apiConfig: ApiConfig,
    private val preference: UserPreference
) {

    private val userId = preference.getId().asLiveData()
    private val userToken = preference.getToken().asLiveData()

    val provinsiId = MutableLiveData<Int>()
    val kabupatenId = MutableLiveData<Int>()

    fun getAllFarm() = userToken.switchMap { token ->
        liveData {
            emit(Result.Loading)
            try {
                val response = apiConfig.getAyamHubApiService(token).getListFarms()
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun getAllBookmark() = userToken.switchMap { token ->
        userId.switchMap { userId ->
            liveData {
                emit(Result.Loading)
                try {
                    val response = apiConfig.getAyamHubApiService(token).getAllBookmark(userId.toInt())
                    emit(Result.Success(response))
                    Log.e("ApiCall", "Test")
                } catch (e: Exception) {
                    emit(Result.Error(e.message.toString()))
                }
            }
        }
    }

    fun checkBookmark(id: Int) = userToken.switchMap { token ->
        userId.switchMap { userId ->
            liveData {
                emit(Result.Loading)
                try {
                    val response = apiConfig.getAyamHubApiService(token).checkBookmark(id, userId.toInt())
                    emit(Result.Success(response))
                } catch (e: Exception) {
                    emit(Result.Error(e.message.toString()))
                }
            }
        }
    }

    fun addBookmark(farmId: Int) = userToken.switchMap { token ->
        userId.switchMap { userId ->
            liveData {
                emit(Result.Loading)
                try {
                    val response = apiConfig.getAyamHubApiService(token).addBookmark(farmId, userId.toInt())
                    emit(Result.Success(response))
                } catch (e: Exception) {
                    emit(Result.Error(e.message.toString()))
                }
            }
        }
    }

    fun removeBookmark(id: Int) = userToken.switchMap { token ->
        liveData {
            emit(Result.Loading)
            try {
                val response = apiConfig.getAyamHubApiService(token).removeBookmark(id)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun getFarmDetail(id: Int) = userToken.switchMap { token ->
        liveData {
            emit(Result.Loading)
            try {
                val response = apiConfig.getAyamHubApiService(token).getFarmDetail(id)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
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
        private var INSTANCE: FarmRepository? = null
        fun getInstance(
            apiConfig: ApiConfig,
            pref: UserPreference
        ) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: FarmRepository(apiConfig, pref)
        }.also { INSTANCE = it }
    }

}