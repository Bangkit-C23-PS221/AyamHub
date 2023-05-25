package com.bangkit.ayamhub.data.repository

import com.bangkit.ayamhub.data.network.retrofit.ApiConfig

class FarmRepository(apiConfig: ApiConfig) {

    companion object {
        private var INSTANCE:  FarmRepository? = null
        fun getInstance(
            apiConfig: ApiConfig
        ) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: FarmRepository(apiConfig)
        }.also { INSTANCE = it }
    }

}