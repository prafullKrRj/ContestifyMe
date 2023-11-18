package com.example.contestifyme.features.profileFeature.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserRatingEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserStatusEntity


@Database(
    entities = [UserInfoEntity::class, UserRatingEntity::class, UserStatusEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ProfileDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: ProfileDatabase? = null
        fun getDatabase(context: Context) : ProfileDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, ProfileDatabase::class.java, "profile_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}