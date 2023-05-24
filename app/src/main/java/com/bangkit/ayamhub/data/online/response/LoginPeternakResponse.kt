package com.bangkit.ayamhub.data.online.response

import com.google.gson.annotations.SerializedName

data class LoginPeternakResponse(

	@field:SerializedName("accessToken")
	val accessToken: String
)
