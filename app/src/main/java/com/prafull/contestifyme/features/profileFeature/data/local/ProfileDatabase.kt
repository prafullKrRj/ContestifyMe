package com.prafull.contestifyme.features.profileFeature.data.local

import androidx.room.Database
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

}