package com.furqoncreative.githubusers3.data.entities.searchuser

import com.furqoncreative.githubusers3.data.entities.userdata.UserData
import com.google.gson.annotations.SerializedName

data class GithubUsersResponse(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<UserData>? = null
)