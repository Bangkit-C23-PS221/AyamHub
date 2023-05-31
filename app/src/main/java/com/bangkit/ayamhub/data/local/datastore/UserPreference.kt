package com.bangkit.ayamhub.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun setToken(token: String) {
        dataStore.edit {
            it[TOKEN] = token
        }
    }

    suspend fun setName(name: String) {
        dataStore.edit {
            it[NAME] = name
        }
    }

    suspend fun setId(id: String) {
        dataStore.edit {
            it[ID] = id
        }
    }

    suspend fun setEmail(email: String) {
        dataStore.edit {
            it[EMAIL] = email
        }
    }

    suspend fun setPhone(phone: String) {
        dataStore.edit {
            it[PHONE] = phone
        }
    }

    suspend fun setLevel(level: String) {
        dataStore.edit {
            it[LEVEL] = level
        }
    }

    fun getId(): Flow<String> {
        return dataStore.data.map {
            it[ID] ?: ""
        }
    }

    fun getName(): Flow<String> {
        return dataStore.data.map {
            it[NAME] ?: ""
        }
    }

    fun getEmail(): Flow<String> {
        return dataStore.data.map {
            it[EMAIL] ?: ""
        }
    }

    fun getPhone(): Flow<String> {
        return dataStore.data.map {
            it[PHONE] ?: ""
        }
    }

    fun getLevel(): Flow<String> {
        return dataStore.data.map {
            it[LEVEL] ?: ""
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map {setting ->
            setting[TOKEN] ?: ""
        }
    }

    companion object {
        private val TOKEN = stringPreferencesKey("token")
        private val ID = stringPreferencesKey("id")
        private val NAME = stringPreferencesKey("name")
        private val PHONE = stringPreferencesKey("phone")
        private val EMAIL = stringPreferencesKey("email")
        private val LEVEL = stringPreferencesKey("level")

        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}