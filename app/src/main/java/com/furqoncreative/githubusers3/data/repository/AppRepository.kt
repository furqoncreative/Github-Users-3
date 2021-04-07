package com.furqoncreative.githubusers3.data.repository

import com.furqoncreative.githubusers3.data.remote.RemoteDataSource
import com.furqoncreative.githubusers3.utils.remoteOnlyOperation
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    fun getSearchUsers(username: String?) = remoteOnlyOperation(
        networkCall = { remoteDataSource.getSearchUser(username) },
    )

    fun getUser(username: String?) = remoteOnlyOperation(
        networkCall = { remoteDataSource.getUser(username) }
    )

    fun getFollowingUsers(username: String?) = remoteOnlyOperation(
        networkCall = { remoteDataSource.getFollowingUser(username) },
    )

    fun getFollowersUsers(username: String?) = remoteOnlyOperation(
        networkCall = { remoteDataSource.getFollowersUser(username) },
    )

}