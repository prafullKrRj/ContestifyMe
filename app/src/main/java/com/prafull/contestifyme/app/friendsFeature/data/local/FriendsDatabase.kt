package com.prafull.contestifyme.app.friendsFeature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [FriendEntity::class], version = 1)
@TypeConverters(FriendConverter::class)
abstract class FriendsDatabase : RoomDatabase() {
    abstract fun friendsDao(): FriendsDao
}