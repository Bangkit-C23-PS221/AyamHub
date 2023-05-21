package com.bangkit.ayamhub.helpers.injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.ayamhub.data.local.datastore.UserPreference
import com.bangkit.ayamhub.data.online.retrofit.ApiConfig
import com.bangkit.ayamhub.data.repository.FarmRepository


private val Context.datastore: DataStore<Preferences> by preferencesDataStore("user")
object Injection {
    fun provideRepository(context: Context): FarmRepository {
        val pref = UserPreference.getInstance(context.datastore)
        val apiService = ApiConfig.getApiService()
        return FarmRepository(pref, apiService)
    }
}