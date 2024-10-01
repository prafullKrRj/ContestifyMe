package com.prafull.contestifyme.app.friendsFeature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity


@Database(entities = [UserInfoEntity::class], version = 1, exportSchema = false)
abstract class FriendsDB : RoomDatabase() {
    abstract fun friendsDao(): FriendsDao
}
