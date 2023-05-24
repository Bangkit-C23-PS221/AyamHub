package com.bangkit.ayamhub.helpers.injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.ayamhub.data.local.datastore.UserPreference
import com.bangkit.ayamhub.data.online.retrofit.ApiConfig
import com.bangkit.ayamhub.data.repository.FarmRepository
import com.bangkit.ayamhub.data.repository.UserRepository


private val Context.datastore: DataStore<Preferences> by preferencesDataStore("user")
object Injection {
    fun provideFarmRepository(): FarmRepository {
        val apiConfig = ApiConfig
        return FarmRepository(apiConfig)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.datastore)
        val apiConfig = ApiConfig
        return UserRepository(apiConfig, pref)
    }
}