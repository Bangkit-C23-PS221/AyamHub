package com.bangkit.ayamhub.data.network.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(
	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("id")
	val id: String
)

