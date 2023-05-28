package com.bangkit.ayamhub.data.network.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    const val FARM_URL = "https://ayamhub.et.r.appspot.com"
    const val LOCATION_URL = "https://ibnux.github.io/data-indonesia/"

    fun getAyamHubApiService(token: String = ""): ApiService = provideApiService(token, FARM_URL)

    fun getLocationApiService():ApiService = provideApiService("", LOCATION_URL)

    private fun provideApiService(token: String = "", baserUrl: String): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val headerInterceptor = Interceptor {
            var request = it.request()
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer: $token")
                .build()
            it.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baserUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}