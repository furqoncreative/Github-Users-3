package com.furqoncreative.favoriteuser.data.local

import android.net.Uri
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData

class ContentUri {

    companion object {
        private const val AUTHORITY = "com.furqoncreative.githubusers3"
        private const val TABLE_NAME = UserData.TABLE_NAME
        private const val SCHEME = "content"


        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }
}