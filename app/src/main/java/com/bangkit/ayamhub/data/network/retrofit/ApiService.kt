package com.bangkit.ayamhub.data.network.retrofit

import com.bangkit.ayamhub.data.network.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @Multipart
    @POST("/farms/{id}/createFarms")
    suspend fun createFarm(
        @Path("id") id: Int,
        @Part file: MultipartBody.Part,
        @Part("name_farm") name: RequestBody,
        @Part("type_chicken") type: RequestBody,
        @Part("price_chicken") price: RequestBody,
        @Part("age_chicken") age: RequestBody,
        @Part("weight_chicken") weight: RequestBody,
        @Part("stock_chicken") stock: RequestBody,
        @Part("desc_farm") note: RequestBody,
        @Part("address_farm") address: RequestBody,
        @Part("status") status: RequestBody,
        @Part("photo_url") photoUrl: RequestBody? = null
    ): MessageResponse

    @Multipart
    @PUT("/updateFarms/{id})")
    suspend fun updateMyFarm(
        @Path("id") id: Int,
        @Part file: MultipartBody.Part,
        @Part("name_farm") name: RequestBody,
        @Part("type_chicken") type: RequestBody,
        @Part("price_chicken") price: RequestBody,
        @Part("age_chicken") age: RequestBody,
        @Part("weight_chicken") weight: RequestBody,
        @Part("stock_chicken") stock: RequestBody,
        @Part("desc_farm") note: RequestBody,
        @Part("address_farm") address: RequestBody,
        @Part("status") status: RequestBody,
        @Part("photo_url") photoUrl: RequestBody? = null
    ) : MessageResponse

//    @GET("/detail-farms/{id}") TODO: Replace with this once the new api is deployed
    @GET("/detail-farms/{id}")
    suspend fun getFarmDetail(
        @Path("id") id: Int
    ) : DetailFarmResponse

    @GET("/farms")
    suspend fun getListFarms (): List<FarmItemResponse>

    @GET("/farms/{id}")
    suspend fun getMyFarm(
        @Path("id") id: Int
    ): List<MyFarmResponse>

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
    suspend fun getAllBookmark(
        @Path("id") id: Int
    ): List<BookmarkResponse>

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