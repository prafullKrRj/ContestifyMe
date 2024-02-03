package com.prafull.contestifyme.features.friendsFeature.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [FriendsDataEntity::class], version = 1)
@TypeConverters(FriendsTypeConverters::class)
abstract class FriendsDB: RoomDatabase() {
    abstract fun friendsDao(): FriendsDao

    companion object {
        @Volatile
        private var INSTANCE: FriendsDB? = null
        fun getDatabase(context: Context): FriendsDB {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FriendsDB::class.java,
                    "friends_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}