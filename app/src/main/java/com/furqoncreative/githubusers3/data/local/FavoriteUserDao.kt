package com.furqoncreative.githubusers3.data.local

import android.database.Cursor
import androidx.room.*
import com.furqoncreative.githubusers3.data.entities.userdata.UserData

@Dao
interface FavoriteUserDao {

    @Query("SELECT * FROM UserData")
    fun getAllFavorite(): Cursor

    @Query("SELECT * FROM UserData WHERE id = :id")
    fun getFavoriteById(id: Int?): Cursor

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: UserData?): Long

    @Query("DELETE FROM UserData WHERE id = :id")
    fun deleteFavorite(id: Int?): Int

}