package com.bangkit.ayamhub.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[TOKEN] = token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map {setting ->
            setting[TOKEN] ?: ""
        }
    }

    suspend fun deleteToken() {
        dataStore.edit {
            it[TOKEN] = ""
        }
    }

    companion object {
        private val TOKEN = stringPreferencesKey("token")

        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<androidx.datastore.preferences.core.Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}