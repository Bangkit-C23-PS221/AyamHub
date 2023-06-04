package com.bangkit.ayamhub.data.network.retrofit

import com.bangkit.ayamhub.data.network.response.*
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("/login-users")
    suspend fun signIn (
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("/regist-users")
    suspend fun signUp (
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("phone") phone: String
    ): MessageResponse

    @GET("/detail-bookmarks/{id}")
    suspend fun getFarmDetail(
        @Path("id") id: Int
    ) : DetailFarmResponse

    @GET("/farms")
    suspend fun getListFarms (): List<FarmItemResponse>

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

    @GET("/bookmarks/{id}")
    suspend fun getAllBookmark(@Path("id") id: Int): List<BookmarkResponse>

    @POST("/bookmarks/{userId}/{farmId}")
    suspend fun addBookmark(
        @Path("farmId") farmId: Int,
        @Path("userId") userId: Int
    ): MessageResponse

    @DELETE("/delete-bookmarks/{userId}/farmId")
    suspend fun removeBookmark(
        @Path("farmId") farmId: Int,
        @Path("userId") userId: Int
    ): MessageResponse

    @POST("/check-bookmarks/{userId}/{farmId}")
    suspend fun checkBookmark(
        @Path("farmId") farmId: Int,
        @Path("userId") userId: Int
    ): BookmarkCheckResponse
}