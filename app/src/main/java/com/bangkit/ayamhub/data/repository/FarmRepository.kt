package com.bangkit.ayamhub.data.repository

import androidx.lifecycle.liveData
import com.bangkit.ayamhub.data.network.retrofit.ApiConfig
import com.bangkit.ayamhub.data.network.Result

class FarmRepository(apiConfig: ApiConfig) {
    fun getAllFarm(token: String) = liveData {
        emit(Result.Loading)
        try {
            emit("Something")
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
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