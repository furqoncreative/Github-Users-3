package com.furqoncreative.favoriteuser.data.remote

import com.furqoncreative.favoriteuser.data.entities.followers.FollowersItem
import com.furqoncreative.favoriteuser.data.entities.followings.FollowingsItem
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AppService {


    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String?): Response<UserData>

    @GET("users/{username}/followers")
    suspend fun getFollowersUser(@Path("username") username: String?): Response<List<FollowersItem>>

    @GET("users/{username}/following")
    suspend fun getFollowingUser(@Path("username") username: String?): Response<List<FollowingsItem>>

}