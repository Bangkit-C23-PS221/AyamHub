package com.bangkit.ayamhub.data.network.response

import com.google.gson.annotations.SerializedName

data class BookmarkCheckResponse(

	@field:SerializedName("id_bookmark")
	val idBookmark: Int? = null,

	@field:SerializedName("isBookmarked")
	val isBookmarked: Boolean,

	@field:SerializedName("message")
	val message: String
)
