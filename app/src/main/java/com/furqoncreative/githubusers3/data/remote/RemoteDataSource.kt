package com.furqoncreative.githubusers3.data.remote

import com.furqoncreative.githubusers3.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val appService: AppService,
) : BaseDataSource() {

    suspend fun getSearchUser(username: String?) = getResult { appService.getSearchUser(username) }

    suspend fun getUser(username: String?) = getResult { appService.getUser(username) }

    suspend fun getFollowersUser(username: String?) =
        getResult { appService.getFollowersUser(username) }

    suspend fun getFollowingUser(username: String?) =
        getResult { appService.getFollowingUser(username) }


}