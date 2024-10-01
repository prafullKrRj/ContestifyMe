package com.prafull.contestifyme.app.profileFeature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prafull.contestifyme.app.profileFeature.data.local.entities.ProfileTypeConverter
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserRatingEntity
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserSubmissionsEntity


@Database(
    entities = [UserInfoEntity::class, UserRatingEntity::class, UserSubmissionsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ProfileTypeConverter::class)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

}