package com.prafull.contestifyme.features.contestsFeature.data.local

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
}