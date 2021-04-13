package com.furqoncreative.githubusers3.data.entities.userdata

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class UserData(

    @PrimaryKey
    @field:SerializedName("id")
    @ColumnInfo(name = COLUMN_ID)
    var id: Int? = null,

    @field:SerializedName("login")
    @ColumnInfo(name = COLUMN_LOGIN)
    var login: String? = null,

    @field:SerializedName("name")
    @ColumnInfo(name = COLUMN_NAME)
    var name: String? = null,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = COLUMN_AVATAR_URL)
    var avatarUrl: String? = null,

    @field:SerializedName("company")
    @ColumnInfo(name = COLUMN_COMPANY)
    var company: String? = null,

    @field:SerializedName("location")
    @ColumnInfo(name = COLUMN_LOCATION)
    var location: String? = null,

    @field:SerializedName("public_repos")
    @ColumnInfo(name = COLUMN_PUBLIC_REPOS)
    var publicRepos: Int? = null,

    @field:SerializedName("followers")
    @ColumnInfo(name = COLUMN_FOLLOWERS)
    var followers: Int? = null,

    @field:SerializedName("following")
    @ColumnInfo(name = COLUMN_FOLLOWING)
    var following: Int? = null,

    ) : Parcelable {
    companion object {
        const val TABLE_NAME = "DetailUser"
        const val COLUMN_ID = "id"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_NAME = "name"
        const val COLUMN_AVATAR_URL = "avatar_url"
        const val COLUMN_COMPANY = "company"
        const val COLUMN_LOCATION = "location"
        const val COLUMN_PUBLIC_REPOS = "public_repos"
        const val COLUMN_FOLLOWING = "following"
        const val COLUMN_FOLLOWERS = "followers"
    }
}
