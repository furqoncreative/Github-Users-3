package com.furqoncreative.githubusers3.data.remote

import com.furqoncreative.githubusers3.data.entities.detailuser.DetailUserResponse
import com.furqoncreative.githubusers3.data.entities.followers.FollowersItem
import com.furqoncreative.githubusers3.data.entities.followings.FollowingsItem
import com.furqoncreative.githubusers3.data.entities.searchuser.SearchUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppService {
    @GET("search/users?")
    suspend fun getSearchUser(@Query("q") username: String?): Response<SearchUserResponse>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String?): Response<DetailUserResponse>

    @GET("users/{username}/followers")
    suspend fun getFollowersUser(@Path("username") username: String?): Response<List<FollowersItem>>

    @GET("users/{username}/following")
    suspend fun getFollowingUser(@Path("username") username: String?): Response<List<FollowingsItem>>
}