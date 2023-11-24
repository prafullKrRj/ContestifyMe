package com.example.contestifyme.features.contestsFeature.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ContestsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ContestDB: RoomDatabase() {
    abstract fun contestsDao(): ContestsDao

    companion object {
        @Volatile
        private var INSTANCE: ContestDB? = null
        fun getInstance(context: Context): ContestDB {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ContestDB::class.java,
                    "contests_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}