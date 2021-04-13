package com.furqoncreative.githubusers3.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.furqoncreative.githubusers3.data.entities.userdata.UserData
import com.furqoncreative.githubusers3.data.local.AppDatabase
import com.furqoncreative.githubusers3.data.local.FavoriteUserDao
import com.furqoncreative.githubusers3.helper.MappingHelper

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.furqoncreative.githubusers3"
        private const val SCHEME = "content"
        private const val ID_FAVORITE_DATA = 1
        private const val ID_FAVORITE_ITEM = 2

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(UserData.TABLE_NAME)
            .build()

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(
                AUTHORITY,
                UserData.TABLE_NAME,
                ID_FAVORITE_DATA
            )
            MATCHER.addURI(AUTHORITY,
                UserData.TABLE_NAME +
                        "/#", ID_FAVORITE_ITEM)
        }
    }

    private lateinit var favoriteDao: FavoriteUserDao

    override fun onCreate(): Boolean {
        context?.let {
            favoriteDao = AppDatabase.getDatabase(it).favoriteUserDao()
        }
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        return when (MATCHER.match(uri)) {
            ID_FAVORITE_DATA -> favoriteDao.getAllFavorite()
            ID_FAVORITE_ITEM -> favoriteDao.getFavoriteById(uri.lastPathSegment!!.toInt())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val addId: Long = when (ID_FAVORITE_DATA) {
            MATCHER.match(uri) -> favoriteDao.insertFavorite(values?.let {
                MappingHelper.convertFromContentValues(
                    it
                )
            })
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$addId")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val deleteId: Int = when (ID_FAVORITE_ITEM) {
            MATCHER.match(uri) -> favoriteDao.deleteFavorite(uri.lastPathSegment!!.toInt())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleteId
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?,
    ): Int {
        return 0
    }

}