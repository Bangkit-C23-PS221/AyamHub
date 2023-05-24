package com.bangkit.ayamhub.data.repository

import com.bangkit.ayamhub.data.local.datastore.UserPreference
import com.bangkit.ayamhub.data.online.retrofit.ApiConfig
import com.bangkit.ayamhub.data.online.retrofit.ApiService

class UserRepository(
    apiConfig: ApiService,
    preference: UserPreference
) {
}