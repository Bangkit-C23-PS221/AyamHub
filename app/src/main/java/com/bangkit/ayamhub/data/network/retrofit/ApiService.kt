package com.bangkit.ayamhub.data.network.retrofit

import com.bangkit.ayamhub.data.network.response.LoginResponse
import com.bangkit.ayamhub.data.network.response.RegisterResponse
import com.bangkit.ayamhub.experimental.dropdown.LocationResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("/login-farms")
    suspend fun signInPeternak (
        @Field("email_farm") email: String,
        @Field("pass_farm") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("/regist-farms")
    suspend fun signUpPeternak (
        @Field("name_farm") name: String,
        @Field("username_farm") username: String,
        @Field("pass_farm") password: String,
        @Field("email_farm") email: String,
        @Field("tlp_farm") phone: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("/login-umkm")
    suspend fun signInUMKM (
        @Field("email_umkm") email: String,
        @Field("pass_umkm") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("/regist-umkm")
    suspend fun signUpUMKM (
        @Field("name_umkm") name: String,
        @Field("username_umkm") username: String,
        @Field("pass_umkm") password: String,
        @Field("email_umkm") email: String,
        @Field("tlp_umkm") phone: String
    ): RegisterResponse

    @GET("provinsi.json")
    suspend fun getProvince(): List<LocationResponse>

    @GET("kabupaten/{id}.json")
    suspend fun getKabupaten(
        @Path("id") id: Int
    ): List<LocationResponse>

    @GET("kecamatan/{id}.json")
    suspend fun getKecamatan(
        @Path("id") id: Int
    ): List<LocationResponse>

}