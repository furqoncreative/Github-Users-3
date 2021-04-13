package com.furqoncreative.favoriteuser.helper

import android.content.ContentValues
import android.database.Cursor
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData.Companion.COLUMN_AVATAR_URL
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData.Companion.COLUMN_COMPANY
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData.Companion.COLUMN_FOLLOWERS
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData.Companion.COLUMN_FOLLOWING
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData.Companion.COLUMN_ID
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData.Companion.COLUMN_LOCATION
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData.Companion.COLUMN_LOGIN
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData.Companion.COLUMN_NAME
import com.furqoncreative.favoriteuser.data.entities.userdata.UserData.Companion.COLUMN_PUBLIC_REPOS

class MappingHelper {
    companion object {
        fun mapCursorToArrayLits(favoritesCursor: Cursor?): ArrayList<UserData> {
            val favoriteList = ArrayList<UserData>()
            favoritesCursor?.apply {
                while (moveToNext()) {
                    val userData = UserData()
                    userData.id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                    userData.login = getString(getColumnIndexOrThrow(COLUMN_LOGIN))
                    userData.name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                    userData.avatarUrl = getString(getColumnIndexOrThrow(COLUMN_AVATAR_URL))
                    userData.company = getString(getColumnIndexOrThrow(COLUMN_COMPANY))
                    userData.location = getString(getColumnIndexOrThrow(COLUMN_LOCATION))
                    userData.publicRepos = getInt(getColumnIndexOrThrow(COLUMN_PUBLIC_REPOS))
                    userData.followers = getInt(getColumnIndexOrThrow(COLUMN_FOLLOWERS))
                    userData.following = getInt(getColumnIndexOrThrow(COLUMN_FOLLOWING))
                    favoriteList.add(userData)
                }
            }

            return favoriteList
        }


        fun convertToContentValues(userData: UserData): ContentValues {
            val contentValues = ContentValues()
            contentValues.put(COLUMN_ID, userData.id)
            contentValues.put(COLUMN_LOGIN, userData.login)
            contentValues.put(COLUMN_NAME, userData.name)
            contentValues.put(COLUMN_AVATAR_URL, userData.avatarUrl)
            contentValues.put(COLUMN_COMPANY, userData.company)
            contentValues.put(COLUMN_LOCATION, userData.location)
            contentValues.put(COLUMN_PUBLIC_REPOS, userData.publicRepos)
            contentValues.put(COLUMN_FOLLOWERS, userData.followers)
            contentValues.put(COLUMN_FOLLOWING, userData.following)
            return contentValues
        }
    }
}