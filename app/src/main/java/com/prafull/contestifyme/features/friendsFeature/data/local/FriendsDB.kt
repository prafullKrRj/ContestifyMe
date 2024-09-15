package com.prafull.contestifyme.features.friendsFeature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FriendsDataEntity::class], version = 1)
abstract class FriendsDB : RoomDatabase() {
    abstract fun friendsDao(): FriendsDao
}