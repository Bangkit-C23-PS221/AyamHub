package com.bangkit.ayamhub.data.online.retrofit

import com.bangkit.ayamhub.data.online.response.LoginPeternakResponse
import com.bangkit.ayamhub.data.online.response.RegisterPeternakResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/login-farms")
    suspend fun signInPeternak (
        @Field("email_farm") email: String,
        @Field("pass_farm") password: String
    ) : LoginPeternakResponse

    @POST("/regist-farms")
    suspend fun signUpPeternak (
        @Field("name_farm") name: String,
        @Field("username_farm") username: String,
        @Field("pass_farm") password: String,
        @Field("email_farm") email: String,
        @Field("tlp_farm") phone: String
    ) : RegisterPeternakResponse

}