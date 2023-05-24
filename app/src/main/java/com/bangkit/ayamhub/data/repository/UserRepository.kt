package com.bangkit.ayamhub.data.repository

import androidx.lifecycle.liveData
import com.bangkit.ayamhub.data.local.datastore.UserPreference
import com.bangkit.ayamhub.data.online.Result
import com.bangkit.ayamhub.data.online.retrofit.ApiConfig

class UserRepository(
    private val apiConfig: ApiConfig,
    private val preference: UserPreference
) {

    suspend fun signInPeternak (email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val response = apiConfig.getApiService().signInPeternak(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun signUpPeternak (
        name: String,
        username: String,
        password: String,
        email: String,
        phoneNumber: String
    ) = liveData {
        emit(Result.Loading)
        try {
            val response = apiConfig.getApiService().signUpPeternak(
                name,
                username,
                password,
                email,
                phoneNumber
            )
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}