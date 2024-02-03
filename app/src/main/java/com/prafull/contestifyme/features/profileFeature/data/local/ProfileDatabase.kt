package com.prafull.contestifyme.features.profileFeature.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prafull.contestifyme.features.profileFeature.data.local.entities.ProfileTypeConverters
import com.prafull.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity


@Database(
    entities = [UserInfoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ProfileTypeConverters::class)
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