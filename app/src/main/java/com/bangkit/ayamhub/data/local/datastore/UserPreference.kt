package com.bangkit.ayamhub.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


class UserPreference (private val dataStore: DataStore<Preferences>){

    companion object {
        const val SESSION_SETTING = "session_setting"
        const val TOKEN_SETTING = "token_setting"

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