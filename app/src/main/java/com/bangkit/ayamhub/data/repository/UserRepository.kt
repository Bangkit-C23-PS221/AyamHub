package com.bangkit.ayamhub.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.bangkit.ayamhub.data.local.datastore.UserPreference
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.data.network.retrofit.ApiConfig

class UserRepository(
    private val apiConfig: ApiConfig,
    private val preference: UserPreference
) {

    fun getToken(): LiveData<String> = preference.getToken().asLiveData()
    fun getId(): LiveData<String> = preference.getId().asLiveData()
    fun getName(): LiveData<String> = preference.getName().asLiveData()
    fun getEmail(): LiveData<String> = preference.getEmail().asLiveData()
    fun getPhone(): LiveData<String> = preference.getPhone().asLiveData()
    fun getLevel(): LiveData<String> = preference.getLevel().asLiveData()

    suspend fun setToken(token: String) = preference.setToken(token)
    suspend fun setId(id: String) = preference.setId(id)
    suspend fun setName(name: String) = preference.setName(name)
    suspend fun setEmail(email: String) = preference.setEmail(email)
    suspend fun setPhone(phoneNumber: String) = preference.setPhone(phoneNumber)
    suspend fun setLevel(level: String) = preference.setLevel(level)

    fun signInPeternak (email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val response = apiConfig.getAyamHubApiService().signInPeternak(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun signUpPeternak (
        name: String,
        username: String,
        password: String,
        email: String,
        phoneNumber: String
    ) = liveData {
        emit(Result.Loading)
        try {
            val response = apiConfig.getAyamHubApiService().signUpPeternak(
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

    fun signInUmkm (email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val response = apiConfig.getAyamHubApiService().signInUMKM(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun signUpUmkm (
        name: String,
        username: String,
        password: String,
        email: String,
        phoneNumber: String
    ) = liveData {
        emit(Result.Loading)
        try {
            val response = apiConfig.getAyamHubApiService().signUpUMKM(
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

    companion object {
        private var instance: UserRepository? = null
        fun getInstance(
            sharedPreferences: UserPreference,
            apiConfig: ApiConfig
        ) = instance ?: synchronized(this) {
            instance ?: UserRepository(apiConfig, sharedPreferences)
        }.also { instance = it }
    }
}