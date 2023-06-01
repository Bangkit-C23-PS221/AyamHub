package com.bangkit.ayamhub.data.network.retrofit

import com.bangkit.ayamhub.data.network.response.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("/login")
    suspend fun signInUMKM (
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("/regist-users")
    suspend fun signUpUMKM (
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("phone") phone: String
    ): MessageResponse

    @GET("/farms")
    suspend fun getListFarms (): List<ListFarmResponse>

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