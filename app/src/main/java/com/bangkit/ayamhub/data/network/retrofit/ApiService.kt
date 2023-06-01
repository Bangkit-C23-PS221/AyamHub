package com.bangkit.ayamhub.data.network.retrofit

import com.bangkit.ayamhub.data.network.response.*
import retrofit2.http.DELETE
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

    @GET("/detail-bookmarks/{id}")
    suspend fun getFarmDetail(
        @Path("id") id: Int
    ) : DetailFarmResponse

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

    @POST("/bookmarks/{userId}/{farmId}")
    fun addBookmark(
        @Path("farmId") farmId: Int,
        @Path("userId") userId: Int
    ): MessageResponse

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

    //TODO: add endpoint
    @DELETE("")
    suspend fun removeBookmark(id: Int): MessageResponse

    //TODO: add endpoint
    @GET("")
    suspend fun checkBookmark(id: Int): MessageResponse
}