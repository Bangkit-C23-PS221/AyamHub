package com.bangkit.ayamhub.data.network.response

import com.google.gson.annotations.SerializedName

data class BookmarkResponse(

	@field:SerializedName("id_bookmark")
	val idBookmark: Int,

	@field:SerializedName("id_farm")
	val idFarm: Int,

	@field:SerializedName("id_user")
	val idUser: Int,

	@field:SerializedName("tb_farm")
	val farmItem: FarmItem
)

data class FarmItem(

	@field:SerializedName("stock_chicken")
	val stockChicken: String,

	@field:SerializedName("weight_chicken")
	val weightChicken: String,

	@field:SerializedName("price_chicken")
	val priceChicken: String,

	@field:SerializedName("address_farm")
	val addressFarm: String,

	@field:SerializedName("photo_url")
	val photoUrl: String,

	@field:SerializedName("desc_farm")
	val descFarm: String,

	@field:SerializedName("name_farm")
	val nameFarm: String,

	@field:SerializedName("type_chicken")
	val typeChicken: String,

	@field:SerializedName("age_chicken")
	val ageChicken: String,

	@field:SerializedName("status")
	val status: String
)
