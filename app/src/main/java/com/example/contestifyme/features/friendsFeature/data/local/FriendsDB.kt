package com.example.contestifyme.features.friendsFeature.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FriendsDataEntity::class], version = 1)
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