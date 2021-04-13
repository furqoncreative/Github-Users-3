package com.furqoncreative.githubusers3.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furqoncreative.githubusers3.data.entities.userdata.UserData

@Database(entities = [UserData::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "FavoriteUser")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }

}