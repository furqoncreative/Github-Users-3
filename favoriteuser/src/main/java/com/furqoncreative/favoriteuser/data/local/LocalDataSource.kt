package com.furqoncreative.favoriteuser.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.furqoncreative.favoriteuser.data.local.ContentUri.Companion.CONTENT_URI
import com.furqoncreative.favoriteuser.utils.BaseDataSource
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val context: Context,
) : BaseDataSource() {

    fun getAllFavorite(): Cursor? =
        context.contentResolver.query(CONTENT_URI, null, null, null, null)

    fun getFavoriteById(id: Int): Cursor? =
        context.contentResolver.query(Uri.parse("$CONTENT_URI/$id"), null, null, null, null)

    fun insertFavorite(favorite: ContentValues): Uri? =
        context.contentResolver.insert(CONTENT_URI, favorite)

    fun deleteFavorite(id: Int): Int =
        context.contentResolver.delete(Uri.parse("$CONTENT_URI/$id"), null, null)
}