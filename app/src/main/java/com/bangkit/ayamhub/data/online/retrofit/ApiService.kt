package com.bangkit.ayamhub.data.online.retrofit

import com.bangkit.ayamhub.data.online.response.LoginResponse
import com.bangkit.ayamhub.data.online.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiService {

    @POST("/login-farms")
    suspend fun signInPeternak (
        @Field("email_farm") email: String,
        @Field("pass_farm") password: String
    ) : LoginResponse

    @POST("/regist-farms")
    suspend fun signUpPeternak (
        @Field("name_farm") name: String,
        @Field("username_farm") username: String,
        @Field("pass_farm") password: String,
        @Field("email_farm") email: String,
        @Field("tlp_farm") phone: String
    ) : RegisterResponse

    @POST("/login-umkm")
    suspend fun signInUMKM (
        @Field("email_umkm") email: String,
        @Field("pass_umkm") password: String
    ) : LoginResponse

    @POST("/regist-umkm")
    suspend fun signUpUMKKM (
        @Field("name_umkm") name: String,
        @Field("username_umkm") username: String,
        @Field("pass_umkm") password: String,
        @Field("email_umkm") email: String,
        @Field("tlp_umkm") phone: String
    ) : RegisterResponse

}