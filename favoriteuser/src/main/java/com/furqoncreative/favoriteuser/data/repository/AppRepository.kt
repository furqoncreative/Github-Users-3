package com.furqoncreative.favoriteuser.data.repository

import android.content.ContentValues
import com.furqoncreative.favoriteuser.data.local.LocalDataSource
import com.furqoncreative.favoriteuser.data.remote.RemoteDataSource
import com.furqoncreative.favoriteuser.utils.remoteOnlyOperation
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun getUser(username: String?) = remoteOnlyOperation(
        networkCall = { remoteDataSource.getUser(username) }
    )

    fun getFollowingUsers(username: String?) = remoteOnlyOperation(
        networkCall = { remoteDataSource.getFollowingUser(username) },
    )

    fun getFollowersUsers(username: String?) = remoteOnlyOperation(
        networkCall = { remoteDataSource.getFollowersUser(username) },
    )

    fun getAllFavorite() = localDataSource.getAllFavorite()

    fun getFavoriteById(id: Int) = localDataSource.getFavoriteById(id)

    fun setFavorite(favorite: ContentValues) = localDataSource.insertFavorite(favorite)

    fun deleteFavorite(id: Int) = localDataSource.deleteFavorite(id)
}