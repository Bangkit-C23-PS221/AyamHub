package com.bangkit.ayamhub.data.network.response

import com.google.gson.annotations.SerializedName

data class ListFarmResponse(

	@field:SerializedName("id_farm")
	val idFarm: Int,

	@field:SerializedName("stock_chicken")
	val stockChicken: String,

	@field:SerializedName("weight_chicken")
	val weightChicken: String,

	@field:SerializedName("price_chicken")
	val priceChicken: String,

	@field:SerializedName("pic_farm")
	val picFarm: String,

	@field:SerializedName("address_farm")
	val addressFarm: String,

	@field:SerializedName("username_farm")
	val usernameFarm: String,

	@field:SerializedName("desc_farm")
	val descFarm: String,

	@field:SerializedName("type_chicken")
	val typeChicken: String,

	@field:SerializedName("age_chicken")
	val ageChicken: String,

	@field:SerializedName("status")
	val status: String
)
